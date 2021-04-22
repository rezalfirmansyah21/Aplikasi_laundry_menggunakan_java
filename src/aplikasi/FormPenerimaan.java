/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.Timer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author LENOVO-PC
 */
public class FormPenerimaan extends javax.swing.JFrame {
    public Statement st;
    Connection cn = koneksi.getKoneksi();
    String username = session.getUserLogin(); 
    private DefaultTableModel model;
    private DefaultTableModel modeltambah;
    java.util.Date tglsekarang = new java.util.Date();
    private SimpleDateFormat smpdtfmt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    //diatas adalah pengaturan format penulisan, bisa diubah sesuai keinginan.
    private String tanggal = smpdtfmt.format(tglsekarang);
    
    java.util.Date tglsekarang2 = new java.util.Date();
    private SimpleDateFormat smpdtfmt2 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    //diatas adalah pengaturan format penulisan, bisa diubah sesuai keinginan.
    private String tanggal2 = smpdtfmt2.format(tglsekarang2);
    /**
     * Creates new form FormPaket
     */
    public FormPenerimaan() {
        initComponents();
           tgl2.setText(tanggal2);
           tgl.setText(tanggal);
        model = new DefaultTableModel();
        tabelpenerimaan.setModel(model); 
        model.addColumn("ID");
        model.addColumn("Kode");
        model.addColumn("ID Pelanggan");
        model.addColumn("Nama");
        model.addColumn("Paket");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Total");
        model.addColumn("Tanggal");
        
        modeltambah = new DefaultTableModel();
        tabeltambah.setModel(modeltambah); 
        modeltambah.addColumn("id_tmp");
        modeltambah.addColumn("Kode");
        modeltambah.addColumn("ID Pelanggan");
        modeltambah.addColumn("Nama");
        modeltambah.addColumn("Paket");
        modeltambah.addColumn("Harga");
        modeltambah.addColumn("Jumlah");
        modeltambah.addColumn("Total");
        modeltambah.addColumn("Tanggal");
        
        tglpenerimaan.setDateFormat(new SimpleDateFormat("yyyy-MM-dd")); //yyyy-MM-dd
        loadData();
        loadDataTambah();
        kode();
        setJam();
        tampil_combo();
        txtuser.setText(username);
    }
    
public final void setJam(){
ActionListener taskPerformer = new ActionListener() {

            @Override
public void actionPerformed(ActionEvent evt) {
String nol_jam = "", nol_menit = "",nol_detik = "";

java.util.Date dateTime = new java.util.Date();
int nilai_jam = dateTime.getHours();
int nilai_menit = dateTime.getMinutes();
int nilai_detik = dateTime.getSeconds();

if(nilai_jam <= 9) nol_jam= "0";
if(nilai_menit <= 9) nol_menit= "0";
if(nilai_detik <= 9) nol_detik= "0";

String jam = nol_jam + Integer.toString(nilai_jam);
String menit = nol_menit + Integer.toString(nilai_menit);
String detik = nol_detik + Integer.toString(nilai_detik);

lbljam.setText(jam+":"+menit+":"+detik+"");
}
};
new Timer(1000, taskPerformer).start();
}

 public void tampil_combo()
      {
          try{
              Connection conn = koneksi.getKoneksi();
              Statement st = conn.createStatement();
              String sql = "select nama_paket from paket order by nama_paket asc";
              ResultSet rs = st.executeQuery(sql);
              
              while(rs.next()){
                  Object[] ob = new Object[1];
                  ob[0] = rs.getString(1);
                  
                  cpaket.addItem((String) ob[0]);
              }
              rs.close(); st.close();
          }catch(Exception e){
              System.out.println(e.getMessage());
              
          }
      }
      
      public void tampil()
      {
          try{
              Connection conn = koneksi.getKoneksi();
              Statement st = conn.createStatement();
              String sql = "select harga from paket where nama_paket='"+cpaket.getSelectedItem()+"'";
              ResultSet rs = st.executeQuery(sql);
              
              while(rs.next()){
                  Object[] ob = new Object[1];
                  ob[0] = rs.getString(1);
                  
                  txtharga.setText((String) ob[0]);
                  
              }
              rs.close(); st.close();
          }catch(Exception e){
              System.out.println(e.getMessage());
          }
      }
    

    
    private void kode() {
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM penerimaan ORDER by kode desc";
            ResultSet r = s.executeQuery(sql);

            if (r.next()) {
                String nofak = r.getString("kode").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String Nol = "";

                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }

                txtkode.setText("P" + Nol + AN);
            } else {
                txtkode.setText("P0001");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
     
    }
    
     public void loadData() {
       btntambah.setEnabled(true);
        btnubah.setEnabled(false);
        txtkode.setEnabled(true);
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM penerimaan";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[9];
                o[0] = r.getString("id_penerimaan");
                o[1] = r.getString("kode");
                o[2] = r.getString("id");
                o[3] = r.getString("nama");
                o[4] = r.getString("paket");
                o[5] = r.getString("harga");
                o[6] = r.getString("jumlah");
                o[7] = r.getString("total");
                o[8] = r.getString("tanggal");
                        
                model.addRow(o);
            }
            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        }
    }
     
     public void loadDataTambah() {
        modeltambah.getDataVector().removeAllElements();
        modeltambah.fireTableDataChanged();

        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();

            String sql = "SELECT * FROM tmp_penerimaan";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[9];
                o[0] = r.getString("id_tmp");
                o[1] = r.getString("kode");
                o[2] = r.getString("id");
                o[3] = r.getString("nama");
                o[4] = r.getString("paket");
                o[5] = r.getString("harga");
                o[6] = r.getString("jumlah");
                o[7] = r.getString("total");
                o[8] = r.getString("tanggal");
                        
                modeltambah.addRow(o);
            }
            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        }
    } 

     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tgl2 = new javax.swing.JLabel();
        lbljam = new javax.swing.JLabel();
        txtuser = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtid = new javax.swing.JLabel();
        btnhapus = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelpenerimaan = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        tgl = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtcaripelanggan = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        cpaket = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtharga = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txttotal = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtkode = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtnama = new javax.swing.JLabel();
        txt = new javax.swing.JLabel();
        idp = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btntambah_tmp = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeltambah = new javax.swing.JTable();
        btntambah = new javax.swing.JButton();
        btncetak = new javax.swing.JButton();
        tglpenerimaan = new org.freixas.jcalendar.JCalendarCombo();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(0, 153, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Form Penerimaan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(317, 317, 317))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));

        tgl2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tgl2.setForeground(new java.awt.Color(255, 255, 255));
        tgl2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tanggal.png"))); // NOI18N
        tgl2.setText("Tanggal");

        lbljam.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbljam.setForeground(new java.awt.Color(255, 255, 255));
        lbljam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/jam.png"))); // NOI18N
        lbljam.setText("Jam");

        txtuser.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtuser.setForeground(new java.awt.Color(255, 255, 255));
        txtuser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N
        txtuser.setText("User");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(tgl2)
                .addGap(271, 271, 271)
                .addComponent(txtuser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbljam)
                .addGap(89, 89, 89))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tgl2)
                    .addComponent(lbljam)
                    .addComponent(txtuser))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 204));
        jLabel2.setText("ID Pelangggan");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 204));
        jLabel4.setText("Paket");

        txtid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtid.setForeground(new java.awt.Color(0, 153, 204));
        txtid.setText("..........");

        btnhapus.setBackground(new java.awt.Color(255, 255, 255));
        btnhapus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnhapus.setForeground(new java.awt.Color(0, 153, 204));
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btnubah.setBackground(new java.awt.Color(255, 255, 255));
        btnubah.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnubah.setForeground(new java.awt.Color(0, 153, 204));
        btnubah.setText("Ubah");
        btnubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnubahActionPerformed(evt);
            }
        });

        tabelpenerimaan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelpenerimaan.setSelectionBackground(new java.awt.Color(0, 153, 204));
        tabelpenerimaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpenerimaanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelpenerimaan);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 153, 204));
        jButton4.setText("Kembali");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 153, 204));
        jButton5.setText("Exit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tgl.setBackground(new java.awt.Color(255, 255, 255));
        tgl.setForeground(new java.awt.Color(255, 255, 255));

        txtcari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtcari.setForeground(new java.awt.Color(0, 153, 204));
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 204));
        jLabel6.setText("Cari data");

        txtcaripelanggan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtcaripelanggan.setForeground(new java.awt.Color(0, 153, 204));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 204));
        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cpaket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "        [- Pilih paket -]" }));
        cpaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cpaketActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 204));
        jLabel5.setText("Harga");

        txtharga.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtharga.setForeground(new java.awt.Color(0, 153, 204));
        txtharga.setText("......");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 204));
        jLabel7.setText("Jumlah");

        txtjumlah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtjumlah.setForeground(new java.awt.Color(0, 153, 204));
        txtjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjumlahActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 204));
        jLabel8.setText("kg");

        txttotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttotal.setForeground(new java.awt.Color(0, 153, 204));
        txttotal.setText(".....");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 204));
        jLabel10.setText("Tanggal");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 204));
        jLabel11.setText("Kode Penerimaan");

        txtkode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtkode.setForeground(new java.awt.Color(0, 153, 204));
        txtkode.setText("..........");

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 204));
        jButton2.setText("Hitung");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 204));
        jLabel9.setText("Total");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 204));
        jLabel12.setText("Nama");

        txtnama.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtnama.setForeground(new java.awt.Color(0, 153, 204));
        txtnama.setText("............");

        txt.setBackground(new java.awt.Color(255, 255, 255));
        txt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt.setForeground(new java.awt.Color(255, 255, 255));

        idp.setBackground(new java.awt.Color(255, 255, 255));
        idp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        idp.setForeground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));

        btntambah_tmp.setBackground(new java.awt.Color(0, 153, 204));
        btntambah_tmp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btntambah_tmp.setForeground(new java.awt.Color(255, 255, 255));
        btntambah_tmp.setText("Tambah");
        btntambah_tmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambah_tmpActionPerformed(evt);
            }
        });

        tabeltambah.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeltambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltambahMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeltambah);

        btntambah.setBackground(new java.awt.Color(0, 153, 204));
        btntambah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btntambah.setForeground(new java.awt.Color(255, 255, 255));
        btntambah.setText("Simpan");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btncetak.setBackground(new java.awt.Color(0, 153, 204));
        btncetak.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btncetak.setForeground(new java.awt.Color(255, 255, 255));
        btncetak.setText("Cetak");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncetak, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btntambah_tmp, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btntambah_tmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntambah)
                    .addComponent(btncetak))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnubah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtcari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)))
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tglpenerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtnama, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(cpaket, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addGap(36, 36, 36)
                                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(txtharga, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel7)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel8)))
                                            .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                                        .addComponent(tgl)
                                        .addGap(91, 91, 91))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtkode, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(idp, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(163, 163, 163)))
                                .addComponent(txtcaripelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtkode)
                    .addComponent(txtcaripelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idp, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(tgl))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txtid))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtnama))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cpaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtharga)
                            .addComponent(jLabel5))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttotal)
                            .addComponent(jButton2)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(tglpenerimaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnubah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
          if(txtid.getText().equals("") ||txtnama.getText().equals("") || txtharga.getText().equals("") || txtjumlah.getText().equals("") || txttotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Rumah Laundry", JOptionPane.INFORMATION_MESSAGE);
        }else{
        try {
            String sql ="delete from penerimaan where id_penerimaan='"+idp.getText()+"'";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            kode();
            JOptionPane.showMessageDialog(this, "berhasil di hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        loadData();
       
        txtnama.setText("");
      //  buttonGroup1.clearSelection();
        txtharga.setText("");
       
        }
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btntambah_tmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambah_tmpActionPerformed
        // TODO add your handling code here:
           if(txtkode.getText().equals("") ||txtid.getText().equals("") ||txtnama.getText().equals("") || txtharga.getText().equals("")|| txtjumlah.getText().equals("") || txttotal.getText().equals("")){
            JOptionPane.showMessageDialog(null, "LENGKAPI DATA !", "Rumah Laundry", JOptionPane.INFORMATION_MESSAGE);
        }else{
               
            String kodee = txtkode.getText();
            String idd = txtid.getText();
            String namaa = txtnama.getText();
            String pakett = (String)cpaket.getSelectedItem();
            String hargaa = txtharga.getText();
            String jml = txtjumlah.getText();
            String totall = txttotal.getText();
            String tgll = (String)tglpenerimaan.getSelectedItem();
           
            
            try {
                Connection c = koneksi.getKoneksi();

                String sql = "INSERT INTO tmp_penerimaan VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement p = c.prepareStatement(sql);
                p.setString(1, null);
                p.setString(2, kodee);
                p.setString(3, idd);
                p.setString(4, namaa);
                p.setString(5, pakett);
                p.setString(6, hargaa);
                p.setString(7, jml);
                p.setString(8, totall);
                p.setString(9, tgll);
                
                p.executeUpdate();
                p.close();
                kode();
            } catch (SQLException e) {
                System.out.println("Terjadi Error");
            } finally {
                loadDataTambah();
              
             //   txtnama.setText("");
            //    buttonGroup1.clearSelection();
                txtharga.setText("");
                txtjumlah.setText("");
                txttotal.setText("");
           
                JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan", "Rumah Laundry", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }//GEN-LAST:event_btntambah_tmpActionPerformed

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        // TODO add your handling code here:
        int i = tabelpenerimaan.getSelectedRow();
        if (i == -1) {
            return;
        }
        String idpp = (String) model.getValueAt(i, 0);
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "UPDATE penerimaan SET kode =  '" + txtkode.getText() + "',id='" + txtid.getText() +"',nama='" + txtnama.getText() +"',paket='" + cpaket.getSelectedItem() +"',harga='" + txtharga.getText() +"',jumlah='" + txtjumlah.getText() +"',total='" + txttotal.getText() +"',tanggal='" + tglpenerimaan.getSelectedItem() +"' WHERE  id_penerimaan ='" + idpp + "'";
            PreparedStatement p = c.prepareStatement(sql);
            p.executeUpdate();
            p.close();
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        } finally {
            loadData();
            txtid.setText("");
            txtnama.setText("");
         //   buttonGroup1.clearSelection();
            txtharga.setText("");
            btntambah.setEnabled(true);
            JOptionPane.showMessageDialog(null, "Data berhasil diubah", "Rumah Laundry", JOptionPane.INFORMATION_MESSAGE);
            txtid.requestFocus();
        }
        
    }//GEN-LAST:event_btnubahActionPerformed

    private void tabelpenerimaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpenerimaanMouseClicked
        // TODO add your handling code here:
        btntambah.setEnabled(false);
        btnubah.setEnabled(true);
     //   txtid.setEnabled(false);
        int i = tabelpenerimaan.getSelectedRow();
        if (i == -1) {
            return;
        }
        String idpp = (String) model.getValueAt(i, 0);
        idp.setText(idpp);
        
        String kodee = (String) model.getValueAt(i, 1);
        txtkode.setText(kodee);

        String idd = (String) model.getValueAt(i, 2);
        txtid.setText(idd);
        
        String namaa = (String) model.getValueAt(i, 3);
        txtnama.setText(namaa);

        String pakett = (String) model.getValueAt(i, 4);
        cpaket.setSelectedItem(pakett);
        
        String hargaa = (String) model.getValueAt(i, 5);
        txtharga.setText(hargaa);
        
        String jml = (String) model.getValueAt(i, 6);
        txtjumlah.setText(jml);
        
        String totall = (String) model.getValueAt(i, 7);
        txttotal.setText(totall);
        
        String tgll = (String) model.getValueAt(i, 8);
        tglpenerimaan.setSelectedItem(tgll);

    }//GEN-LAST:event_tabelpenerimaanMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
           home n = new home(); 
                    n.setVisible(true);
                    this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        // TODO add your handling code here:
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();

            String sql = "select * from penerimaan where kode like '%" + txtcari.getText() + "%' or id like'%" + txtcari.getText() + "%' or nama like'" + txtcari.getText()  + "%' or paket like'" + txtcari.getText()  + "%' or harga like'" + txtcari.getText()  + "%' or jumlah like'" + txtcari.getText()  + "%' or total like'" + txtcari.getText()  + "%' or tanggal like'" + txtcari.getText() +"%'";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[9];
                o[0] = r.getString("id_penerimaan");
                o[1] = r.getString("kode");
                o[2] = r.getString("id");
                o[3] = r.getString("nama");
                o[4] = r.getString("paket");
                o[5] = r.getString("harga");
                o[6] = r.getString("jumlah");
                o[7] = r.getString("total");
                o[8] = r.getString("tanggal");
                       
                model.addRow(o);
            }
            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Terjadi Error");
        }
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjumlahActionPerformed

    private void cpaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cpaketActionPerformed
        // TODO add your handling code here:
        tampil();
    }//GEN-LAST:event_cpaketActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int a=Integer.parseInt(txtharga.getText());
        int b=Integer.parseInt(txtjumlah.getText());
     
        int hitung=a*b;
        txttotal.setText(String.valueOf(hitung));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String cri = txtcaripelanggan.getText();
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            com.mysql.jdbc.Connection koneksi = (com.mysql.jdbc.Connection) DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/laundry", "root", "");
            com.mysql.jdbc.Statement statement = (com.mysql.jdbc.Statement) koneksi.createStatement();
            String sql="SELECT * FROM pelanggan WHERE id like '"+cri+"'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()){
                String idd = rs.getString("id");
                String nm = rs.getString("nama");

                txtid.setText(idd);
                txtnama.setText(nm);
            }else{
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan");

                txtnama.setText("");

                statement.close();
                koneksi.close();
            }
        }catch(Exception ex){
            //            JOptionPane.showMessageDialog(null,"Data tidak ditemukan"+ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tabeltambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltambahMouseClicked
        // TODO add your handling code here:
            int jawaban;
        if ((jawaban = JOptionPane.showConfirmDialog(null,"Yakin batal?", "Konfirmasi", JOptionPane.YES_NO_OPTION)) == 0) {
            try{

                int i = tabeltambah.getSelectedRow();
                if (i == -1) {
                    return;
                }
                String id = (String) modeltambah.getValueAt(i, 0);

                st = cn.createStatement();
                st.executeUpdate("delete from tmp_penerimaan where id_tmp = '"+id+ "'");

                 //  kode();
                loadDataTambah();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tabeltambahMouseClicked

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
     
                try {
                    Connection c = koneksi.getKoneksi();
                    Statement s = c.createStatement();

                    String sql = "SELECT * FROM tmp_penerimaan";
                    ResultSet r = s.executeQuery(sql);

                    while (r.next()) {
//                        long millis=System.currentTimeMillis();
//                        java.sql.Date date=new java.sql.Date(millis);
//                        System.out.println(date);
//                        String tgl = date.toString();
                        String sqla = "INSERT INTO penerimaan VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement p = c.prepareStatement(sqla);
                     
                        p.setString(1, null);
                        p.setString(2, txtkode.getText());
                        p.setString(3, r.getString("id"));
                        p.setString(4, r.getString("nama"));
                        p.setString(5, r.getString("paket"));
                        p.setString(6, r.getString("harga"));
                        p.setString(7, r.getString("jumlah"));
                        p.setString(8, r.getString("total"));
                        p.setString(9, r.getString("tanggal"));
                      
                        p.executeUpdate();
                        p.close();
                    

                    }
                    r.close();
                    s.close();
                        loadData();
                    
                } catch (SQLException e) {
                    System.out.println("Terjadi Error");
                }finally{
                    try {
                        String sqla ="TRUNCATE `tmp_penerimaan";
                        java.sql.Connection conn=(Connection)koneksi.getKoneksi();
                        java.sql.PreparedStatement pst=conn.prepareStatement(sqla);
                        pst.execute();
                        JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Rumah Laundry", JOptionPane.INFORMATION_MESSAGE);
                        loadDataTambah();
                         
                        txt.setText(txtkode.getText());
                        btncetak.setEnabled(true);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, e.getMessage());
                    }
                }
            
        
    }//GEN-LAST:event_btntambahActionPerformed

    private static String path = System.getProperty("user.dir")+"/src/laporan/";
    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
            String filename = path+"strukpenerimaan.jrxml";
        JasperReport jasrep;
        JasperPrint japri;
        JasperViewer javie = null;
        HashMap param = new HashMap(2);
        JasperDesign jasdes;
        try {
            param.put("transaksi",txtkode.getText());
            param.put("user",txtuser.getText());
            File report = new File(filename);
            jasdes = JRXmlLoader.load(report);
            jasrep = JasperCompileManager.compileReport(jasdes);
            japri = JasperFillManager.fillReport(jasrep,param,aplikasi.koneksi.getKoneksi());
            javie.viewReport(japri,false);
            kode();
        } catch (Exception e) {
            System.out.print("gagal ngprint");
        }
    }//GEN-LAST:event_btncetakActionPerformed

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPenerimaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPenerimaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPenerimaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPenerimaan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPenerimaan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncetak;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btntambah_tmp;
    private javax.swing.JButton btnubah;
    private javax.swing.JComboBox<String> cpaket;
    private javax.swing.JLabel idp;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbljam;
    private javax.swing.JTable tabelpenerimaan;
    private javax.swing.JTable tabeltambah;
    private javax.swing.JLabel tgl;
    private javax.swing.JLabel tgl2;
    private org.freixas.jcalendar.JCalendarCombo tglpenerimaan;
    private javax.swing.JLabel txt;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtcaripelanggan;
    private javax.swing.JLabel txtharga;
    private javax.swing.JLabel txtid;
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JLabel txtkode;
    private javax.swing.JLabel txtnama;
    private javax.swing.JLabel txttotal;
    private javax.swing.JLabel txtuser;
    // End of variables declaration//GEN-END:variables
}
