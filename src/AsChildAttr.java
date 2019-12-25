public enum AsChildAttr {
    MANDATORY("MANDATORY"),
    OPTIONAL("OPTIONAL");
    String name;

    AsChildAttr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AsChildAttr typeOf(String name) {
        for (AsChildAttr attr : AsChildAttr.values())
            if (name.equals(attr.getName()))
                return attr;
        return null;
    }
}
