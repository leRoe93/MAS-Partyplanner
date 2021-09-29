package ntswwm.platform;

import java.util.HashMap;

import jade.core.Profile;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.gateway.JadeGateway;

public class AgentPlatform {

    public static Runtime RUNTIME;
    public static ExtendedProperties PROPERTIES;
    public static ContainerController CONTAINER_CONTROLLER;
    public static Profile MAIN_PROFILE;
    public static HashMap<String, AgentController> AGENTS;
    public static JadeGateway GATEWAY;
}
