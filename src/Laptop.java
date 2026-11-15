import java.math.BigDecimal;

public class Laptop implements Product {
    private final ProductInfo info;
    private final int ram;
    private final String color;

    private Laptop(Builder builder) {
        this.info = builder.info;
        this.ram = builder.ram;
        this.color = builder.color;
    }

    @Override
    public String getId() {
        return info.getId();
    }

    @Override
    public String getDescription() {
        return info.getName() + " (" + ram + " GB RAM, " + color + ")";
    }

    @Override
    public BigDecimal getPrice() {
        BigDecimal extra = ram == 16 ? new BigDecimal("100.00") : BigDecimal.ZERO;
        return info.getPrice().add(extra);
    }

    public int getRam() {
        return ram;
    }

    public String getColor() {
        return color;
    }

    public static class Builder {
        private final ProductInfo info;
        private int ram = 8;
        private String color = "Black";

        public Builder(ProductInfo info) {
            if (info == null) {
                throw new IllegalArgumentException("Product information is required.");
            }
            this.info = info;
        }

        public Builder ram(int ram) {
            this.ram = ram;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Laptop build() {
            if (ram != 8 && ram != 16) {
                throw new IllegalArgumentException("RAM must be 8 or 16 GB.");
            }
            if (!"Black".equals(color) && !"Silver".equals(color)) {
                throw new IllegalArgumentException("Color must be Black or Silver.");
            }
            return new Laptop(this);
        }
    }
}
