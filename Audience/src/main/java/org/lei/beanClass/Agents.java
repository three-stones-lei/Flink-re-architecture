package org.lei.beanClass;

import java.util.Arrays;

/**
 * ClassName: Agents
 * Package: org.lei.beanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 12:55 pm
 * @Version 1.0
 */
public class Agents {
    private Agent[] agents;

    public Agents() {
    }

    public Agent[] getAgents() {
        return agents;
    }

    public void setAgents(Agent[] agents) {
        this.agents = agents;
    }

    @Override
    public String toString() {
        return "Agents{" +
                "agents=" + Arrays.toString(agents) +
                '}';
    }
}
