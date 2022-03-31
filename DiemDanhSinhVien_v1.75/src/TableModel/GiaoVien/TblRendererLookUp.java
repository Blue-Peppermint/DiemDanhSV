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
        // Giải thích về các tham số đầu vào của method này:
        // 1. Object value, int row, int column
        // Vì đây là class render JTable - nên nó liên quan đến UI. Còn class table Model liên quan đến dữ liệu
        // Nên row + column ở trong TABLEMODEL.getValueAt() != CELLRENDERER.getTableCellRendererComponent()
        // Cụ thể ở trong trường hợp có kết hợp sử dụng với RowFilter. Khi sử dụng RowFilter thì nó sẽ tìm kiếm và hiện
        // thông tin mong muốn lên JTable thì khi sau khi tìm thấy nó sẽ render lại tất cả thông tin đó
        // Khi đó: ở TABLEMODEL.getValueAt() nó vẫn sẽ lấy row + column thực tế (nghĩa là row + column khi JTable ở trạng thái mới khởi tạo ban đầu)
        // Nhưng CELLRENDERER.getTableCellRendererComponent() lại lấy row + column theo giao diện mà người dùng thấy (nghĩa là row + column được tính lại dựa vào những gì User thấy)
        // Tuy nhiên THAM SỐ "value" trong CELLRENDERER.getTableCellRendererComponent() vẫn == value mà TABLEMODEL.getValueAt() return
        // TỔNG KẾT: VẬY NÊN NẾU JTABLE CÓ SỬ DỤNG RowFilter thì khi chủ động sử dụng (chứ CPU tự renderer thì getValueAt ko bị lỗi)
        // method TABLEMODEL.getValueAt(row, col). Nhớ convert row, col trước
        // để lấy giá trị chính xác nhất. Thông qua table.convertRowIndexToModel(row);
        // =============================================================================================================================================================================
        // 2.boolean isSelected, boolean hasFocus
        // isSelected vs hasFocus: + đều giống nhau ở chỗ, giá trị sẽ thay đổi phụ thuộc vào row mình chọn
        // + khác nhau: Lúc renderer jtable thì sẽ render lần lượt từng column trong row, hết column thì xuống row kế tiếp
        // thì giá trị của isSelected + hasFocus sẽ không giống nhau mỗi lần render từng cell như vậy, cụ thể:
        // isSelected luôn true với mọi column ở trong row được chọn
        // hasFocus chỉ true với một mình column ở trong row được chọn (chỉ true với 1 cell được chọn thôi)
        // bởi vì căn bản khi chọn vào 1 row nào đó trong jtable thì sẽ có 2 hiệu ứng: set UI hàng được chọn(isSelected), set UI cell được chọn (hasFocus)    

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
            // Do khi render row có kết hợp với RowFilter nên phải convert về row thật để lấy mssv chính xác
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


