package model;

public class Equipment {
    private int equipmentId;
    private String type;
    private String make;
    private String model;

    public Equipment(String type, String make, String model) {
        this.type = type;
        this.make = make;
        this.model = model;
    }

    // Getters and Setters

    public int getEquipmentId() { return equipmentId; }
    public void setEquipmentId(int equipmentId) { this.equipmentId = equipmentId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipmentId=" + equipmentId +
                ", type='" + type + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
