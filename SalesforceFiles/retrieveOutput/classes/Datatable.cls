/**
 * Datatable
 * This class is meant to replicate the Google visualization javascript object.
 * It is returned by the graph request handler to the source of the request,
 * and the resulting Datatable can be used to draw a graph using the Google 
 * Chart API
 */
global class Datatable
{
    private List<Column> cols;  //the column information
    private List<Row> rows;     //rows of data
    
    /**
     * Constructor for a Datatable.
     * Columns MUST be specified at construction time and cannot be changed afterward.
     * This is because we want as much as possible for the table to stay consistent
     * while rows are being added to it. Changing the number of columns or the name of
     * a column in the middle of building a table is very likely to result in an invalid
     * table and there's really no legitimate reason to do it.
     */
    
    public Datatable(List<Column> cols)
    {
        //create a deep copy of the list so the client can't change this Datatable's columns
        List<Column> deepCopy = new List<Column>();
        for(Column col : cols)
        {
            deepCopy.add(col);
        }
        
        //assign the deep copy to our column information
        this.cols = deepCopy;
        this.rows = new List<Row>();
    }
    
    /**
     * Adds a row of data to the data table.
     * If values is shorter than the number of columns in the data table, the remaining
     * cells will be filled with null values. If values is longer than the number of columns
     * the values in the extra cells will be ignored. The type of each value should match
     * to the type field specified in the corresponding column, but addRow does not currently
     * check for this.
     */
    public void addRow(List<Object> values)
    {
        //a list of cells to hold the new row information
        List<Cell> newRowCells = new List<Cell>();
        
        //loop through values, but don't read more cells than we have columns
        for(Integer currentCell = 0; currentCell < this.cols.size(); currentCell++)
        {
            //if there is a value for the cell, assign that, otherwise assign null
            Object val = (currentCell < values.size()) ? values.get(currentCell) : null;
            newRowCells.add(new Cell(val));
        }
        
        //add the new row to our list of rows
        this.rows.add(new Row(newRowCells));
    }
    
    /** returns the number of rows in this datatable */
    public Integer getRowCount()
    {
        return rows.size();
    }
    
    public Object getValueAt(Integer row, Integer col)
    {
        return rows.get(row).getCell(col).getVal();
    }
    /**
     * Represents a single cell of data in a data table.
     */
    public class Cell
    {
        private Object v;
        public Cell(Object val)
        {
            this.v = val;
        }
        public Object getVal()
        {
            return this.v;
        }
    }
    
    /**
     * Encapsulates information about each column of a table.
     */
    public class Column
    {
        private String id;
        private String type;
        private String label;
        public Column(String id, String type, String label){
            this.id = id;
            this.type = type;
            this.label = label;
        }
    }
    
    /**
     * Contains the cells for one row of a table.
     */
    public class Row
    {
        private List<Cell> c;
        public Row(List<Cell> cells)
        {
            this.c = cells;
        }
        public Cell getCell(Integer col)
        {
            return c.get(col);
        }
    }
}