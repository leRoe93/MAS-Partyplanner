package ntswwm.platform;

import java.util.ArrayList;

import jade.core.Profile;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class AgentPlatform {

    public static Runtime RUNTIME;
    public static ExtendedProperties PROPERTIES;
    public static ContainerController CONTAINER_CONTROLLER;
    public static Profile MAIN_PROFILE;
    public static ArrayList<AgentController> AGENTS;
}
