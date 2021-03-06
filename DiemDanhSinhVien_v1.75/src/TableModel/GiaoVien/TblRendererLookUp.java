/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TableModel.GiaoVien;

import TableModel.GiaoVien.RollUpStudentTableModel;
import Model.GiaoVien.ListBuoiHoc;
import Model.GiaoVien.ListBuoiHoc;
import Model.MyEnum;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author chuon
 */
// renderer nay danh cho jtable column = String.Class + Interger.Class va co tuong tac voi SoBuoiHoc, SoBuoiVang,...
//<editor-fold>
public class TblRendererLookUp extends DefaultTableCellRenderer {
    private static final Color firstRowBackgroundColor = new Color(0xE8F2FE); //light blue
    private static final Color secondRowBackgroundColor = new Color(0xFFFFFF) ;
    private static final Color rowForegroundColor = Color.BLACK;
    private static final Color hightLightForegroundColor = Color.WHITE;
    private static final Color hightLightBackgroundColor = new Color(255, 77, 77);
    private static final Color selectedForegroundColor = Color.WHITE;
    private static final Color selectedBackgroundColor = new Color(51, 204, 255);
    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private ListBuoiHoc listTongBuoiHoc;

    private static final int  COLUMN_STT = 0;
    private static final int COLUMN_MSSV = 4; // trong jtable thi columnIndex chua MSSV la 4
    private static int soBuoiHoc;
    private static int soBuoiHocMuon;
    private static int soBuoiVangKoPhep;
    private static int soBuoiVangCoPhep;

    public TblRendererLookUp(ListBuoiHoc listTongBuoiHoc) {
        setFont(new java.awt.Font("Tahoma", 0, 22));
        setOpaque(true);
        this.listTongBuoiHoc = listTongBuoiHoc;
        this.soBuoiHoc = -1;
        this.soBuoiHocMuon = -1;
        this.soBuoiVangKoPhep = -1;
        this.soBuoiVangCoPhep = -1;
    }
    
    
    public TblRendererLookUp(ListBuoiHoc listTongBuoiHoc, int soBuoiHoc,
            int soBuoiMuon, int soBuoiVangKoPhep, int soBuoiVangCoPhep) {
        setFont(new java.awt.Font("Tahoma", 0, 22));
        setOpaque(true);
        this.listTongBuoiHoc = listTongBuoiHoc;
        this.soBuoiHoc = soBuoiHoc;
        this.soBuoiHocMuon = soBuoiMuon;
        this.soBuoiVangKoPhep = soBuoiVangKoPhep;
        this.soBuoiVangCoPhep = soBuoiVangCoPhep;
    }

    public void setSoBuoiHoc(int soBuoiHoc) {
        this.soBuoiHoc = soBuoiHoc;
    }

    public void setSoBuoiHocMuon(int soBuoiHocMuon) {
        this.soBuoiHocMuon = soBuoiHocMuon;
    }

    public void setSoBuoiVangKoPhep(int soBuoiVangKoPhep) {
        this.soBuoiVangKoPhep = soBuoiVangKoPhep;
    }

    public void setSoBuoiVangCoPhep(int soBuoiVangCoPhep) {
        this.soBuoiVangCoPhep = soBuoiVangCoPhep;
    }    

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Gi???i th??ch v??? c??c tham s??? ?????u v??o c???a method n??y:
        // 1. Object value, int row, int column
        // V?? ????y l?? class render JTable - n??n n?? li??n quan ?????n UI. C??n class table Model li??n quan ?????n d??? li???u
        // N??n row + column ??? trong TABLEMODEL.getValueAt() != CELLRENDERER.getTableCellRendererComponent()
        // C??? th??? ??? trong tr?????ng h???p c?? k???t h???p s??? d???ng v???i RowFilter. Khi s??? d???ng RowFilter th?? n?? s??? t??m ki???m v?? hi???n
        // th??ng tin mong mu???n l??n JTable th?? khi sau khi t??m th???y n?? s??? render l???i t???t c??? th??ng tin ????
        // Khi ????: ??? TABLEMODEL.getValueAt() n?? v???n s??? l???y row + column th???c t??? (ngh??a l?? row + column khi JTable ??? tr???ng th??i m???i kh???i t???o ban ?????u)
        // Nh??ng CELLRENDERER.getTableCellRendererComponent() l???i l???y row + column theo giao di???n m?? ng?????i d??ng th???y (ngh??a l?? row + column ???????c t??nh l???i d???a v??o nh???ng g?? User th???y)
        // Tuy nhi??n THAM S??? "value" trong CELLRENDERER.getTableCellRendererComponent() v???n == value m?? TABLEMODEL.getValueAt() return
        // T???NG K???T: V???Y N??N N???U JTABLE C?? S??? D???NG RowFilter th?? khi ch??? ?????ng s??? d???ng (ch??? CPU t??? renderer th?? getValueAt ko b??? l???i)
        // method TABLEMODEL.getValueAt(row, col). Nh??? convert row, col tr?????c
        // ????? l???y gi?? tr??? ch??nh x??c nh???t. Th??ng qua table.convertRowIndexToModel(row);
        // =============================================================================================================================================================================
        // 2.boolean isSelected, boolean hasFocus
        // isSelected vs hasFocus: + ?????u gi???ng nhau ??? ch???, gi?? tr??? s??? thay ?????i ph??? thu???c v??o row m??nh ch???n
        // + kh??c nhau: L??c renderer jtable th?? s??? render l???n l?????t t???ng column trong row, h???t column th?? xu???ng row k??? ti???p
        // th?? gi?? tr??? c???a isSelected + hasFocus s??? kh??ng gi???ng nhau m???i l???n render t???ng cell nh?? v???y, c??? th???:
        // isSelected lu??n true v???i m???i column ??? trong row ???????c ch???n
        // hasFocus ch??? true v???i m???t m??nh column ??? trong row ???????c ch???n (ch??? true v???i 1 cell ???????c ch???n th??i)
        // b???i v?? c??n b???n khi ch???n v??o 1 row n??o ???? trong jtable th?? s??? c?? 2 hi???u ???ng: set UI h??ng ???????c ch???n(isSelected), set UI cell ???????c ch???n (hasFocus)    

        if (value instanceof Integer) {
            if (column != COLUMN_STT) {
                int cellModelValue = (int) value;
                ImageIcon icon = MyEnum.getIcon(cellModelValue);
                setIcon(icon);
                setText("");
//                setHorizontalAlignment(SwingConstants.CENTER);
//                setVerticalAlignment(SwingConstants.CENTER);
                setHorizontalAlignment(CENTER);
                //setVerticalAlignment(SwingConstants.CENTER);
            }
            else {
                setIcon(null);
                setText(String.valueOf(value));
                setHorizontalAlignment(LEFT);
            }
            
        } else if (value instanceof String) {
            setIcon(null);
            setText((String) value);
            setHorizontalAlignment(LEFT);
        }
        if (!isSelected) {
            if (row % 2 == 1) {
                setBackground(firstRowBackgroundColor);
            } else {
                setBackground(secondRowBackgroundColor);
            }
            setForeground(rowForegroundColor);
            // soBuoi == -1 thi ko kiem tra filter
            // neu so luong khau kiem tra = so luong khau kiem tra hop le thi hightLight
            int numberOfFilters = 0;
            int numberOfValidFilters = 0;
            RollUpStudentTableModel model = (RollUpStudentTableModel) table.getModel();
            // Do khi render row c?? k???t h???p v???i RowFilter n??n ph???i convert v??? row th???t ????? l???y mssv ch??nh x??c
            int modelRow = table.convertRowIndexToModel(row);     
            if (soBuoiHoc != -1) {
                numberOfFilters++;
                String mssv = (String) model.getValueAt(modelRow, COLUMN_MSSV);
                if (listTongBuoiHoc.kiemTraSVHoc(mssv, soBuoiHoc) == true) {
                    numberOfValidFilters++;
                }
            }
            if (soBuoiHocMuon != -1) {
                numberOfFilters++;
                String mssv = (String) model.getValueAt(modelRow, COLUMN_MSSV);
                if (listTongBuoiHoc.kiemTraSVHocMuon(mssv, soBuoiHocMuon) == true) {
                    numberOfValidFilters++;
                }
            }
            if (soBuoiVangKoPhep != -1) {
                numberOfFilters++;
                String mssv = (String) model.getValueAt(modelRow, COLUMN_MSSV);
                if (listTongBuoiHoc.kiemTraSVVangKoPhep(mssv, soBuoiVangKoPhep) == true) {
                    numberOfValidFilters++;
                }
            }
            if (soBuoiVangCoPhep != -1) {
                numberOfFilters++;
                String mssv = (String) model.getValueAt(modelRow, COLUMN_MSSV);
                if (listTongBuoiHoc.kiemTraSVVangCoPhep(mssv, soBuoiVangCoPhep) == true) {
                    numberOfValidFilters++;
                }
            }
            if (numberOfFilters > 0 && numberOfFilters == numberOfValidFilters) {
                setBackground(hightLightBackgroundColor);
                setForeground(hightLightForegroundColor);
            }
        } else {
            setForeground(selectedForegroundColor);
            setBackground(selectedBackgroundColor);
        }
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(noFocusBorder);
        }
        return this;

    }
}
//</editor-fold>


