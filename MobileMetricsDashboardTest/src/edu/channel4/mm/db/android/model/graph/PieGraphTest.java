package edu.channel4.mm.db.android.model.graph;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import junit.framework.TestCase;

import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.ValueType;

public class PieGraphTest extends TestCase {

   protected void setUp() throws Exception {
   }

   protected void tearDown() throws Exception {
   }
   
   public void testParseDataTable() throws TypeMismatchException {
      // Not going to mock the DataTable value object
      // http://www.mockobjects.com/2007/04/test-smell-everything-is-mocked.html
      
      // create used values, mocks or otherwise
      DataTable table = new DataTable();
      CategorySeries mockCategorySeries = mock(CategorySeries.class);
      DefaultRenderer mockRenderer = mock(DefaultRenderer.class);
      
      // set up DataTable value object. As per first comment, it's not a mock.
      ValueType valueType1 = ValueType.TEXT;
      ValueType valueType2 = ValueType.NUMBER;
      ColumnDescription columnDesc1 = new ColumnDescription("id1", valueType1, "label1");
      ColumnDescription columnDesc2 = new ColumnDescription("id2", valueType2, "label2");
      table.addColumn(columnDesc1);
      table.addColumn(columnDesc2);
      
      TableRow tableRow1 = new TableRow();
      tableRow1.addCell("someValue");
      tableRow1.addCell(10.5);
      
      TableRow tableRow2 = new TableRow();
      tableRow2.addCell("someOtherValue");
      tableRow2.addCell(5.01);
      
      table.addRow(tableRow1);
      table.addRow(tableRow2);
      
      // call the method under test
      PieGraph.parseDataTable(table, mockCategorySeries, mockRenderer);
      
      // verify activity on the mocks
      verify(mockCategorySeries).add("someValue", 10.5);
      verify(mockCategorySeries).add("someOtherValue", 5.01);
      verify(mockRenderer, times(2)).addSeriesRenderer(any(SimpleSeriesRenderer.class));
   }

}
