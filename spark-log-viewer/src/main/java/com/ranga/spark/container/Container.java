package com.ranga.spark.container;/* rangareddy.avula created on 10/05/20 */

public class Container extends ContainerBase {

    private String capacity;
    private boolean isRegisteredWithDriver;
    private Executor executor;
    private String driverHost;

    public Container(String containerInfo) {
        super(containerInfo);
    }

    public void buildContainer(String dateTime, String logType, String serviceName, String message) {
        if (serviceName.contains("memory.MemoryStore")) {
            if (message.contains("MemoryStore started with capacity ")) {
                capacity = message.replace("MemoryStore started with capacity ", "");
            }
        } else if (serviceName.equals("executor.CoarseGrainedExecutorBackend")) {
            if (message.contains("Connecting to driver")) {
                driverHost = message.replace("Connecting to driver: ", "").trim();
            } else if (message.equals("Successfully registered with driver")) {
                isRegisteredWithDriver = true;
            }
        } else if (serviceName.contains("executor.Executor")) {
            if (message.startsWith("Starting executor ID")) {
                String[] executorSplit = message.replace("Starting executor ID ", "").split(" ");
                String id = executorSplit[0];
                String host = executorSplit[3];
                executor = new Executor(id, host, dateTime);
            } else {
                executor.getLogs().add(message);
            }
        }
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public boolean isRegisteredWithDriver() {
        return isRegisteredWithDriver;
    }

    public void setRegisteredWithDriver(boolean registeredWithDriver) {
        isRegisteredWithDriver = registeredWithDriver;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public String getDriverHost() {
        return driverHost;
    }

    public void setDriverHost(String driverHost) {
        this.driverHost = driverHost;
    }

    @Override
    public String toString() {
        return "Container{" +
                ", capacity='" + capacity + '\'' +
                ", isRegisteredWithDriver=" + isRegisteredWithDriver +
                ", executor=" + executor +
                ", driverHost='" + driverHost + '\'' +
                ", id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}