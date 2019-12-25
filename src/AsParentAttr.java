public enum AsParentAttr {
    OR("OR"),
    XOR("XOR");
    String name;

    AsParentAttr(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static AsParentAttr typeOf(String name) {
        for (AsParentAttr attr : AsParentAttr.values())
            if (name.equals(attr.getName()))
                return attr;
        return null;
    }
}
