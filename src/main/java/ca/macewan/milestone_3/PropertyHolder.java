package ca.macewan.milestone_3;

public class PropertyHolder {
    private TableViewObject row;
    private PropertyAssessments importedPropertyAssessments;

    public PropertyAssessments getImportedPropertyAssessments() {
        return this.importedPropertyAssessments;
    }

    public void setImportedPropertyAssessments(PropertyAssessments p) {
        this.importedPropertyAssessments = p;
    }


    private final static PropertyHolder INSTANCE = new PropertyHolder();

    private PropertyHolder() {}

    public static PropertyHolder getInstance() {
        return INSTANCE;
    }

    public void setProperty(TableViewObject o) {
        this.row = o;
    }

    public TableViewObject getProperty() {
        return this.row;
    }
}
