package Hole;

import Rabbit.Rabbit;

public class RabbitPack {
    private Rabbit rabbit;
    private float deathTime;

    public Rabbit returnRabbit() {
        return rabbit;
    }

    public float getDeathTime() {
        return deathTime;
    }

    public RabbitPack(Rabbit rabbit, float deathTime) {
        this.rabbit = rabbit;
        this.deathTime = deathTime;
    }
}