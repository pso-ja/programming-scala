public @interface TestAnnotation {
    public String name();
    public int value();
    public NestedAnnotation[] nestedValues();
    public NestedAnnotation nestedValue();
}
