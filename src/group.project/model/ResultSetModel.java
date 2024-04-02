package group.project.model;

import java.util.ArrayList;

public class ResultSetModel {
    public ArrayList<String> headers;
    public ArrayList<ArrayList<String>> rows;
    public int numCols;

    public ResultSetModel(ArrayList<String> headers, ArrayList<ArrayList<String>> rows, int numCols) {
        this.headers = headers;
        this.rows = rows;
        this.numCols = numCols;
    }
}
