/* Author: Ray Tam
 *
 * Note to Ray, treat row and cells as private class. This will be refactored after the redesign of the database.
 *
 */

@isTest
private class DatatableTest {
    static testMethod void testDatatable() {
    
        
        List<Datatable.Column> columnList = new List<Datatable.Column>();        
        columnList.add(new Datatable.Column('1', 'Blue', 'Time walk'));
        columnList.add(new Datatable.Column('2', 'Black', 'Pestilence'));
        columnList.add(new Datatable.Column('3', 'Red', 'Stone Rain'));

        Datatable datatable = new Datatable(columnList);

        List<Object> cells = new List<Object>();
        cells.add('about');
        cells.add('time');
        cells.add('for');
        datatable.addRow(cells);
        
        cells = new List<Object>();
        cells.add('first');
        cells.add('second');
        cells.add('third');
        datatable.addRow(cells);
        
        System.assertEquals(2, datatable.getRowCount());
        System.assertEquals('about', (String)datatable.getValueAt(0,0));
        System.assertEquals('time', (String)datatable.getValueAt(0,1));
        System.assertEquals('for', (String)datatable.getValueAt(0,2));

        System.assertEquals('first', (String)datatable.getValueAt(1,0));
        System.assertEquals('second', (String)datatable.getValueAt(1,1));
        System.assertEquals('third', (String)datatable.getValueAt(1,2));
    }
}