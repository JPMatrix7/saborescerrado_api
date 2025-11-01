package saborescerrado.jp.tp2.model;

import jakarta.persistence.Entity;

@Entity
public class Embalagem extends EntityClass {

    private Integer volume;
    
    private String material;

    // Getters and Setters
    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
