package querybuilder;

public interface Column {
    String getTableName();
    String getColumnName();

    default String getQualifiedName() {
        return getTableName() + "." + getColumnName();
    }

    default Column withAlias(String alias) {
        return new Column() {
            @Override
            public String getTableName() {
                return alias;
            }

            @Override
            public String getColumnName() {
                return Column.this.getColumnName();
            }

            @Override
            public String getQualifiedName() {
                return alias + "." + getColumnName();
            }

            @Override
            public String toString() {
                return getQualifiedName();
            }
        };
    }

    default String as(String alias) {
        return getQualifiedName() + " AS " + alias;
    }
}
