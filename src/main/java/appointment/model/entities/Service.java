package appointment.model.entities;

public class Service {
    private int serviceId;
    private String name;
    private String description;
    private int duration;
    private int price;

    public Service(String name) {
        this.name = name;
    }

    public Service(int id) {
        this.serviceId = id;
    }

    public Service(int serviceId, String name, String description, int duration, int price) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }

    public Service(int serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.name = serviceName;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceId=" + serviceId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                '}';
    }
}
