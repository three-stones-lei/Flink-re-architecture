# -*- coding: utf-8 -*- #
# Copyright 2020 Google LLC. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
"""Command line processing utilities for cloud access bindings."""

from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals

from googlecloudsdk.api_lib.accesscontextmanager import util
from googlecloudsdk.api_lib.util import apis
from googlecloudsdk.calliope import exceptions as calliope_exceptions
from googlecloudsdk.core import exceptions as core_exceptions
from googlecloudsdk.core import properties
from googlecloudsdk.core import resources
from googlecloudsdk.core.util import iso_duration
from googlecloudsdk.core.util import times


def AddUpdateMask(ref, args, req):
  """Hook to add update mask."""
  del ref
  update_mask = []
  if args.IsKnownAndSpecified('level'):
    update_mask.append('access_levels')
  if args.IsKnownAndSpecified('dry_run_level'):
    update_mask.append('dry_run_access_levels')

  if not update_mask:
    raise calliope_exceptions.MinimumArgumentException(
        ['--level', '--dry_run_level'])

  req.updateMask = ','.join(update_mask)
  return req


def AddUpdateMaskAlpha(ref, args, req):
  """Hook to add update mask in Alpha track."""
  del ref
  update_mask = []
  if args.IsKnownAndSpecified('level'):
    update_mask.append('access_levels')
  if args.IsKnownAndSpecified('dry_run_level'):
    update_mask.append('dry_run_access_levels')
  if args.IsKnownAndSpecified(
      'restricted_client_application_client_ids'
  ) or args.IsKnownAndSpecified('restricted_client_application_names'):
    update_mask.append('restricted_client_applications')

  if not update_mask:
    raise calliope_exceptions.MinimumArgumentException([
        '--level',
        '--dry_run_level',
        '--restricted_client_application_names',
        '--restricted_client_application_client_ids',
    ])

  req.updateMask = ','.join(update_mask)
  return req


def ProcessOrganization(ref, args, req):
  """Hook to process organization input."""
  del ref, args
  if req.parent is not None:
    return req

  org = properties.VALUES.access_context_manager.organization.Get()
  if org is None:
    raise calliope_exceptions.RequiredArgumentException(
        '--organization', 'The attribute can be set in the following ways: \n' +
        '- provide the argument `--organization` on the command line \n' +
        '- set the property `access_context_manager/organization`')

  org_ref = resources.REGISTRY.Parse(
      org, collection='accesscontextmanager.organizations')
  req.parent = org_ref.RelativeName()
  return req


def ProcessRestrictedClientApplicationsAlpha(unused_ref, args, req):
  """Hook to process restricted client applications input in Alpha track."""
  del unused_ref
  return _ProcessRestrictedClientApplications(args, req, version='v1alpha')


def _ProcessRestrictedClientApplications(args, req, version=None):
  """Process restricted client applications input for the given version."""
  # Processing application client ids if available
  if args.IsKnownAndSpecified('restricted_client_application_client_ids'):
    client_ids = args.restricted_client_application_client_ids
    restricted_client_application_refs = (
        _MakeRestrictedClientApplicationsFromIdentifiers(
            client_ids,
            'restricted_client_application_client_ids',
            version=version,
        )
    )
    # req.gcpUserAccessBinding is None when no access levels are specified
    # during update. Access Levels are optional when updating restricted client
    # applications, but they are required when creating a new binding.
    if req.gcpUserAccessBinding is None:
      req.gcpUserAccessBinding = util.GetMessages(
          version=version
      ).GcpUserAccessBinding()
    for restricted_client_application_ref in restricted_client_application_refs:
      req.gcpUserAccessBinding.restrictedClientApplications.append(
          restricted_client_application_ref
      )
  # processing application names if available
  if args.IsKnownAndSpecified('restricted_client_application_names'):
    client_names = args.restricted_client_application_names
    restricted_client_application_refs = (
        _MakeRestrictedClientApplicationsFromIdentifiers(
            client_names,
            'restricted_client_application_names',
            version=version,
        )
    )
    # req.gcpUserAccessBinding is None when no access levels are specified
    # during update. Access Levels are optional when updating restricted client
    # applications, but they are required when creating a new binding.
    if req.gcpUserAccessBinding is None:
      req.gcpUserAccessBinding = util.GetMessages(
          version=version
      ).GcpUserAccessBinding()
    for restricted_client_application_ref in restricted_client_application_refs:
      req.gcpUserAccessBinding.restrictedClientApplications.append(
          restricted_client_application_ref
      )
  return req


def _MakeRestrictedClientApplicationsFromIdentifiers(
    app_identifiers, arg_name, version=None
):
  """Parse restricted client applications and return their resource references."""
  resource_refs = []
  if app_identifiers is not None:
    app_identifiers = [
        # remove empty strings
        identifier
        for identifier in app_identifiers
        if identifier
    ]
    for app_identifier in app_identifiers:
      if arg_name == 'restricted_client_application_client_ids':
        try:
          resource_refs.append(
              util.GetMessages(version=version).Application(
                  clientId=app_identifier
              )
          )
        except:
          raise calliope_exceptions.InvalidArgumentException(
              '--{}'.format('restricted_client_application_client_ids'),
              'Unable to parse input. The input must be of type string[].',
          )
      elif arg_name == 'restricted_client_application_names':
        try:
          resource_refs.append(
              util.GetMessages(version=version).Application(name=app_identifier)
          )
        except:
          raise calliope_exceptions.InvalidArgumentException(
              '--{}'.format('restricted_client_application_names'),
              'Unable to parse input. The input must be of type string[].',
          )
      else:
        raise calliope_exceptions.InvalidArgumentException(
            '--{}'.format('arg_name'),
            'The input is not valid for Restricted Client Applications.',
        )
  return resource_refs


def _ParseLevelRefs(req, param, is_dry_run):
  """Parse level strings and return their resource references."""
  level_inputs = req.gcpUserAccessBinding.accessLevels
  if is_dry_run:
    level_inputs = req.gcpUserAccessBinding.dryRunAccessLevels

  level_refs = []
  level_inputs = [level_input for level_input in level_inputs if level_input]
  if not level_inputs:
    return level_refs

  arg_name = '--dry_run_level' if is_dry_run else '--level'

  for level_input in level_inputs:
    try:
      level_ref = resources.REGISTRY.Parse(
          level_input,
          params=param,
          collection='accesscontextmanager.accessPolicies.accessLevels')
    except:
      raise calliope_exceptions.InvalidArgumentException(
          '--{}'.format(arg_name),
          'The input must be the full identifier for the access level, '
          'such as `accessPolicies/123/accessLevels/abc`.')
    level_refs.append(level_ref)
  return level_refs


def ProcessLevels(ref, args, req):
  """Hook to format levels and validate all policies."""
  del ref  # Unused
  policies_to_check = {}

  param = {}
  policy_ref = None
  if args.IsKnownAndSpecified('policy'):
    try:
      policy_ref = resources.REGISTRY.Parse(
          args.GetValue('policy'),
          collection='accesscontextmanager.accessPolicies')
    except:
      raise calliope_exceptions.InvalidArgumentException(
          '--policy',
          'The input must be the full identifier for the access policy, '
          'such as `123` or `accessPolicies/123.')
    param = {'accessPoliciesId': policy_ref.Name()}
    policies_to_check['--policy'] = policy_ref.RelativeName()

  # Parse level and dry run level
  level_refs = _ParseLevelRefs(
      req, param,
      is_dry_run=False) if args.IsKnownAndSpecified('level') else []
  dry_run_level_refs = _ParseLevelRefs(
      req, param,
      is_dry_run=True) if args.IsKnownAndSpecified('dry_run_level') else []

  # Validate all refs in each level ref belong to the same policy
  level_parents = [x.Parent() for x in level_refs]
  dry_run_level_parents = [x.Parent() for x in dry_run_level_refs]
  if not all(x == level_parents[0] for x in level_parents):
    raise ConflictPolicyException(['--level'])
  if not all(x == dry_run_level_parents[0] for x in dry_run_level_parents):
    raise ConflictPolicyException(['--dry-run-level'])

  # Validate policies of level, dry run level and policy inputs are the same
  if level_parents:
    policies_to_check['--level'] = level_parents[0].RelativeName()
  if dry_run_level_parents:
    policies_to_check['--dry-run-level'] = dry_run_level_parents[
        0].RelativeName()
  flags_to_complain = list(policies_to_check.keys())
  flags_to_complain.sort()  # Sort for test purpose.
  policies_values = list(policies_to_check.values())
  if not all(x == policies_values[0] for x in policies_values):
    raise ConflictPolicyException(flags_to_complain)

  # Set formatted level fields in the request
  if level_refs:
    req.gcpUserAccessBinding.accessLevels = [
        x.RelativeName() for x in level_refs
    ]
  if dry_run_level_refs:
    req.gcpUserAccessBinding.dryRunAccessLevels = [
        x.RelativeName() for x in dry_run_level_refs
    ]
  return req


def ProcessSessionLength(string):
  """Process the session-length argument into an acceptable form for GCSL reauth settings."""

  duration = times.ParseDuration(string)

  # TODO(b/346781832)
  if duration.total_seconds > iso_duration.Duration(days=1).total_seconds:
    raise calliope_exceptions.InvalidArgumentException(
        '--session-length',
        'The session length cannot be greater than one day.',
    )
  # Format for Google protobuf Duration
  return '{}s'.format(int(duration.total_seconds))


def ProcessReauthSettings(unused_ref, args, req):
  """Hook to process GCSL reauth settings.

    When --session-length=0 make sure the sessionLengthEnabled is set to false.

    Throw an error if --reauth-method or --use-oidc-max-age are set without
    --session-length.

  Args:
      unused_ref: Unused
      args: The command line arguments
      req: The request object

  Returns:
    The modified request object.

  Raises:
    calliope_exceptions.InvalidArgumentException: If arguments are incorrectly
    set.
  """
  del unused_ref
  if args.IsKnownAndSpecified('session_length'):
    # Deformat Google protobuf Duration string, which is a number of seconds
    # terminated with an 's'. Taking the 's' off the end of the string allows us
    # to convert to an int and process the real value.
    session_length = int(
        req.gcpUserAccessBinding.reauthSettings.sessionLength[:-1]
    )
    if session_length == 0:  # Case where we disable reauth
      req.gcpUserAccessBinding.reauthSettings.sessionLengthEnabled = False
    else:  # Normal case
      req.gcpUserAccessBinding.reauthSettings.sessionLengthEnabled = True
  else:
    if args.IsKnownAndSpecified('reauth_method'):
      raise calliope_exceptions.InvalidArgumentException(
          '--reauth_method',
          'Cannot set --reauth_method without --session-length',
      )
    # Clear all default reauth settings from the request if --session-length is
    # unspecified
    req.gcpUserAccessBinding.reauthSettings = None

  return req


class ConflictPolicyException(core_exceptions.Error):
  """For conflict policies from inputs."""

  def __init__(self, parameter_names):
    super(ConflictPolicyException, self).__init__(
        'Invalid value for [{0}]: Please make sure {0} resources are '
        'all from the same policy.'.format(', '.join(
            ['{0}'.format(p) for p in parameter_names])))
