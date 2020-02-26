package com.dongfang.advanced.designpatterns.creational.builder;

public class Builder {
    public class OrbitalModule {
        public OrbitalModule(String name) {
            this.name = name;
        }

        private String name;
    }

    public class Engine {
        public Engine(String name) {
            this.name = name;
        }

        private String name;
    }

    public class EscapeTower {
        public EscapeTower(String name) {
            this.name = name;
        }

        private String name;
    }

    public class AriShip {
        private OrbitalModule orbitalModule;
        private Engine engine;
        private EscapeTower escapeTower;

        public AriShip(OrbitalModule orbitalModule, Engine engine, EscapeTower escapeTower) {
            this.orbitalModule = orbitalModule;
            this.engine = engine;
            this.escapeTower = escapeTower;
        }
    }

    public interface AirShipBuilder {
        Engine buildEngine();

        OrbitalModule buildOrbitalModule();

        EscapeTower buildEscapeTower();
    }

    public interface AirShipDirector {
        AriShip directAirShip();
    }

    // StringBuilder DomBuilder SaxBuilder
    public class ChinaAirShipBuilder implements AirShipBuilder {

        @Override
        public Engine buildEngine() {
            System.out.println("build china Engine");
            return new Engine("china Engine");
        }

        @Override
        public OrbitalModule buildOrbitalModule() {
            System.out.println("build china OrbitalModule");
            return new OrbitalModule("china OrbitalModule");
        }

        @Override
        public EscapeTower buildEscapeTower() {
            System.out.println("build china EscapeTower");
            return new EscapeTower("china EscapeTower");
        }
    }

    public class ChinaAirShipDirector implements AirShipDirector {
        private AirShipBuilder builder;

        public ChinaAirShipDirector(AirShipBuilder builder) {
            this.builder = builder;
        }

        @Override
        public AriShip directAirShip() {
            // 装配者调用构建者，构建各个组件
            Engine engine = builder.buildEngine();
            EscapeTower escapeTower = builder.buildEscapeTower();
            OrbitalModule orbitalModule = builder.buildOrbitalModule();

            // 组装后返回
            return new AriShip(orbitalModule, engine, escapeTower);
        }
    }

}
