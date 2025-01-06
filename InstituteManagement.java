import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread;
import java.lang.Exception;
import java.sql.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;
import java.net.URL;
import java.awt.Desktop;
import javax.swing.table.*;


import java.io.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;


class ExaminationDetails extends JFrame implements ActionListener,ItemListener
{
     private JLabel lbl;
     private JComboBox <String> jcb_rollno;
     private JComboBox <String> jcb_sem;
     private JTextField txt_subject_name,txt_total,txt_total_marks,txt_obtained_marks,txt_name,txt_course,txt_branch;
     private JButton btn;
     private DefaultTableModel table_model;
     private JTable tbl_text;
     private Vector<Vector>rows;
     private Vector<String>cols;
     private JScrollPane sp2;
     private int total_marks;
     private ExaminationDetails me=this;

     private JButton b;

     public ExaminationDetails()
     {
         this.setLayout(null);
         this.getContentPane().setBackground(new Color(255-102-102));
        
         lbl=new JLabel("Examination Result");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(270,20,500,50);
         lbl.setFont(new Font("serif",Font.BOLD,30));
         this.add(lbl);

         lbl=new JLabel("Name");
         lbl.setBounds(50,100,100,30);
         lbl.setForeground(Color.WHITE);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_name=new JTextField();
         txt_name.setBounds(200,100,150,30);
         txt_name.setEditable(false);
         this.add(txt_name);

         lbl=new JLabel("Roll Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,100,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

             jcb_rollno=new JComboBox();
             jcb_rollno.setBounds(600,100,150,30);
             jcb_rollno.setBackground(Color.WHITE);
             this.add(jcb_rollno);
             jcb_rollno.addItem("Select roll_no");

             try 
             {
               DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
               String sql = "select * from student";
               PreparedStatement ps= con.prepareStatement(sql);
               ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
             jcb_rollno.addItem(rs.getString("roll_no"));   
           }
        }
        catch(Exception ex)
        {
        ex.printStackTrace();
        }

         lbl=new JLabel("Semester");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,150,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);
        jcb_sem=new JComboBox<String>();
        jcb_sem.setBounds(200,150,150,30);
        jcb_sem.setBackground(Color.WHITE);
        this.add(jcb_sem);

        String arr[]={"1st Sem","2nd Sem","3rd Sem","4th Sem","5th Sem","6th Sem","7th Sem","8th Sem"};
        for(String item:arr)
        {
          jcb_sem.addItem(item); 
        }

         lbl=new JLabel("Branch");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,150,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_branch = new JTextField();
         txt_branch.setEditable(false);
         txt_branch.setBounds(600,150,150,30);
         this.add(txt_branch);
         

         lbl=new JLabel("Course");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_course=new JTextField();
         txt_course.setEditable(false);
         txt_course.setBounds(200,200,150,30);
         this.add(txt_course);
         jcb_rollno.addItemListener(this);



         lbl=new JLabel("Enter Subject");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,200,150,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_subject_name=new JTextField();
         txt_subject_name.setBounds(600,200,150,30);
         this.add(txt_subject_name);


         lbl=new JLabel("Total Marks");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_total=new JTextField();
         txt_total.setBounds(200,250,150,30);
         this.add(txt_total);

         lbl=new JLabel("Obtained Marks");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,250,150,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);



         txt_obtained_marks=new JTextField();
         txt_obtained_marks.setBounds(600,250,150,30);
         this.add(txt_obtained_marks);
         txt_obtained_marks.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String sub_name = txt_subject_name.getText();
                String total_m = txt_total.getText();
                String obtained_m = txt_obtained_marks.getText();
                
                if(sub_name.isEmpty()==false && total_m.isEmpty()==false && obtained_m.isEmpty()==false)
                {
                    try
                    {

                        Integer total = Integer.parseInt(total_m);
                        
                        Integer obtained = Integer.parseInt(obtained_m);
                        if(obtained>total)
                        {
                            JOptionPane.showMessageDialog(me,"Enter valid marks..");
                        }
                        else 
                        {
                        Vector <String> single_row = new Vector <String>();

                        single_row.add(sub_name);
                        single_row.add(total_m);
                        single_row.add(obtained_m);
                        rows.add(single_row);
                        table_model.setDataVector(rows, cols);
                        txt_subject_name.setText("");
                        txt_total.setText("");
                        txt_obtained_marks.setText("");
                                           
                        total_marks = Integer.parseInt(txt_total_marks.getText()) + obtained;
                        txt_total_marks.setText(total_marks+""); 
                        }                     
                    }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(me,ex);
                    }
                }
                else
                JOptionPane.showMessageDialog(me, "Subject is not selected");
            }
        });

        cols = new Vector <String>();
        String arr1[] = {"SUBJECT NAME", "TOTAL MARKS", "OBTAINED MARKS"};
        for(String text : arr1)
        {
            cols.add(text);
        }

     
         rows=new Vector<Vector>();

        table_model = new DefaultTableModel(rows, cols);
        tbl_text = new JTable(table_model);
        sp2 = new JScrollPane(tbl_text);
        sp2.setBounds(50, 340, 680, 180);
        this.add(sp2);
        tbl_text.getTableHeader().setBackground(Color.WHITE);



        btn=new JButton("Deleted Selected Row");
         btn.setBounds(50,570,150,35);
        this.add(btn);
        btn.addActionListener(this);

         lbl=new JLabel("Marks Obtained ",JLabel.CENTER);
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(200,570,200,35);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_total_marks=new JTextField("0");
         txt_total_marks.setBounds(400,570,150,35);
         this.add(txt_total_marks);
         txt_total_marks.setEditable(false);


        btn=new JButton("Save and Print");
        btn.setBounds(600,570,150,35);
        this.add(btn);
        btn.addActionListener(this);


        b=new JButton("BACK");
        b.setBounds(350,615,150,35);
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        this.add(b);
        b.addActionListener(this);

      
            this.setVisible(true);
            this.setSize(800,750);
            this.setLocation(400,50);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


     }
     public void actionPerformed(ActionEvent e)
     {
        String text = e.getActionCommand();
        switch(text)
        {
        case"Deleted Selected Row":
                 int selected_row_index = tbl_text.getSelectedRow();
                 int obtained=Integer.parseInt(table_model.getValueAt(selected_row_index, 2).toString());
            
                 total_marks = total_marks - obtained;
                txt_total_marks.setText(total_marks+"");
                table_model.removeRow(selected_row_index);

                break;
         
            case"Save and Print":
            try
            {
                    String name = txt_name.getText().trim().toUpperCase();
                    String rn = jcb_rollno.getSelectedItem().toString().trim();
                    String sem = jcb_sem.getSelectedItem().toString().trim();
                    String branch = txt_branch.getText().trim().toUpperCase();
                    String course = txt_course.getText().trim().toUpperCase();


                     
                    if(name.length()==0 && rn.length()==0 && sem.length()==0 && branch.length()==0 && course.length()==0)
                    {
                        JOptionPane.showMessageDialog(this,"Fill the form properly...");
                    }
                    else
                    {
                    
                         String sql = "insert into mprint values(?,?,?)";
                         DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
      
                           
                            PreparedStatement ps = con.prepareStatement(sql);
                           // String rollno = jcb_rollno.getSelectedItem().toString().trim();
                            int n=0;
                            for(Vector single_row: rows)
                            {
                                ps.setString(1, single_row.get(0).toString());
                                ps.setInt(2, Integer.parseInt(single_row.get(1).toString()));
                                ps.setInt(3, Integer.parseInt(single_row.get(2).toString()));
                                 n = ps.executeUpdate();                                                             
                            }
                            ps.close();
                            con.close();

                            if(n == 1)
                            {
                                // creating pdf

                                Document doc = new Document();
                                PdfWriter w = PdfWriter.getInstance(doc, new FileOutputStream("C:/bills_pdf/"+rn+".pdf"));
                                doc.open();

                                // Title
                                com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD, BaseColor.RED);
                                Paragraph p = new Paragraph("***The Learning Palace***\n Marksheet", f);
                                p.setAlignment(1);

                                /*
                                Alignment 
                                0 = left
                                1 = center
                                2 = right
                                */
                                doc.add(p);

                                doc.add(new Paragraph("Student Name: "+name));
                                doc.add(new Paragraph("Student RollNo.: "+rn));
                                doc.add(new Paragraph("Semester: "+sem));
                                doc.add(new Paragraph("Branch: "+branch));
                                doc.add(new Paragraph("Course: "+course));
                                doc.add(new Paragraph("\n\n"));
                                doc.add(new Paragraph("Marks Details:"));


                                PdfPTable marks_table = new PdfPTable(3);
                                marks_table.setWidthPercentage(100);
                                marks_table.setSpacingBefore(11f);
                                marks_table.setSpacingAfter(11f);

                                float col_width[] = {3f, 2f, 2f};
                                marks_table.setWidths(col_width);

                                String cols[] = {"Subject", "Total Marks", "Obtained Marks"};

                                for(int i = 0; i < cols.length; i++)
                                {
                                    PdfPCell c = new PdfPCell(new Paragraph(cols[i], new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 11, com.itextpdf.text.Font.BOLD, BaseColor.BLACK)));
                                    c.setHorizontalAlignment(Element.ALIGN_CENTER);       
                                    marks_table.addCell(c);
                                }

                                for(Vector <String> single_row : rows)
                                {
                                    for(Object item : single_row)
                                    {
                                        PdfPCell c = new PdfPCell(new Paragraph((String)item));
                                        c.setHorizontalAlignment(Element.ALIGN_CENTER);       
                                        marks_table.addCell(c);
                                    }
                                }

                                doc.add(marks_table);

                                p = new Paragraph("Total Marks: "+total_marks);
                                p.setAlignment(2);
                                doc.add(p);

                                p = new Paragraph("*** Best Wishes For Future ***");
                                p.setAlignment(1);
                                doc.add(p);

                    
                                doc.close();
                                w.close();  
                                JOptionPane.showMessageDialog(this, "Marksheet successfully saved..");
                                txt_name.setText(" ");
                                 txt_branch.setText(" ");
                                 txt_branch.setText(" ");
                                 txt_course.setText(" ");
                                 txt_total_marks.setText(" ");
                                 tbl_text.setModel(new DefaultTableModel(null,new String[]{"SUBJECT NAME", "TOTAL MARKS", "OBTAINED MARKS"}));

                           
                        }
                    else
                    JOptionPane.showMessageDialog(this,"Marks not successfully saved..");
                  }
               }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this, "Save and Print: "+ex.toString());
                }
                break;

            case"BACK":
                this.dispose();
                break;
            }
            
            
        
        }


     
    @Override
    public void itemStateChanged(ItemEvent e)
    {
         try 
         {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from student where roll_no = '"+jcb_rollno.getSelectedItem()+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                
                
                txt_name.setText(rs.getString("name"));
                txt_branch.setText(rs.getString("branch"));
                txt_course.setText(rs.getString("course"));
                
               
            }   
            con.close();
            rs.close();
            ps.close();
   
         }
         catch(Exception ex)
         {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
                      
         }

    }
 }


class About extends JFrame
{
    private JTextArea ta;
    public About()
    {

        this.setLayout(null);
     
        ImageIcon i1=new ImageIcon("images/F2.jpg");
        Image i2=i1.getImage().getScaledInstance(350,270,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel lbl=new JLabel(i3);
        lbl.setBounds(350,0,300,200);
        this.add(lbl);

        JLabel heading=new JLabel("The Learning Palace");
        lbl.setForeground(Color.WHITE);
        heading.setBounds(10,20,400,130);
        heading.setFont(new Font("ALGERIAN",Font.BOLD,25));
        this.add(heading);

        ta=new JTextArea(50,30);
        ta.setEditable(false);
        ta.setFont(new Font("Tahoma",Font.BOLD,15));
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.WHITE);
        ta.setBounds(20,200,650,450);
        this.add(ta);
        ta.setText("                        ****MISSION****\n\n 1)To be a student centric institute imbibing experiential, innovative and lifelong \nlearning skills, addressing societal problems.\n2)To promote and undertake all-inclusive research and development.\n3)To inculcate entrepreneurial attitude and values amongst Learners\n4)To strengthen National and International,collaborations for symbiotic relations.\n5)To mentor aspiring Institutions to unleash their potential, towards nation building.\n\n                       *****VISION*****\n\n \nTo be a Value based Globally Recognized Institution ensuring academic\n excellence and fostering Research, Innovation and Entrepreneurial Attitude.\n\nCORE VALUES (S T E E R)\n 1)Strength\n 2)Truth\n 3)Endurance\n 4)Ethics\n 5)Reverence for All");
        
        this.setSize(700,700);
        this.setLocation(400,100);
        this.setVisible(true);
        
    }
}
class FeeStructure extends JFrame 
{  

   private JTable tbl;
   private JScrollPane jsp;
   public FeeStructure()
  {
    
      this.setLayout(null);
      this.getContentPane().setBackground(new Color(255-102-102));
        
      JLabel heading = new JLabel("FEE STRUCTURE");
     heading.setBounds(50,10,400,30);
     heading.setForeground(Color.WHITE);
     heading.setFont(new Font("Tahoma",Font.BOLD,30));
     this.add(heading); 

     tbl=new JTable();
    try
    {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
        String sql=" select * from fee";
        PreparedStatement ps= con.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        tbl.setModel(DbUtils.resultSetToTableModel(rs));
        
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(this,ex);
    }

      jsp=new JScrollPane(tbl);
      jsp.setBounds(0,60,1000,200);
      this.add(jsp);


      this.setSize(1000,700);
      this.setLocation(250,50);
      this.setVisible(true);
  }
}

class StudentFeeForm extends JFrame implements ItemListener,ActionListener
{
    private JComboBox cb_roll_no,cb_course,cb_branch,jcb_student_Sem;
    private JLabel lbl_surname,lbl_name,lbl,lbl_total; 
    private JButton btn;  

    public StudentFeeForm()
    {

        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));
        
        ImageIcon img=new ImageIcon(ClassLoader.getSystemResource("images/fee.jpg"));
        Image i2=img.getImage().getScaledInstance(500,300,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel lbl=new JLabel(i3);
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(400,50,500,300);
        this.add(lbl);

         lbl=new JLabel("Select Roll No");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(40,60,150,20);
         lbl.setFont(new Font("Tahoma",Font.BOLD,15));
         this.add(lbl);

         cb_roll_no=new JComboBox();
         cb_roll_no.setBackground(Color.WHITE);
         cb_roll_no.setBounds(200,60,150,20);
         this.add(cb_roll_no);

         try
         {
                   
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                  String sql="select * from student";
               
                  PreparedStatement ps=con.prepareStatement(sql);
                  ResultSet rs= ps.executeQuery();
                   while(rs.next())
                   {
                     cb_roll_no.addItem(rs.getString("roll_no"));
                   }

                  
         }
         catch(Exception ex)
         {
           JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
         }
    

         lbl=new JLabel("Name");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(40,100,150,20);
         lbl.setFont(new Font("Tahoma",Font.BOLD,15));
         this.add(lbl);

         lbl_name=new JLabel();
         lbl_name.setForeground(Color.WHITE);
         lbl_name.setBounds(200,100,150,20);
         this.add(lbl_name);
         lbl_name.setFont(new Font("Tahoma",Font.BOLD,15));

         lbl=new JLabel("Surname");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(40,140,150,20);
         lbl.setFont(new Font("Tahoma",Font.BOLD,15));
         this.add(lbl);

         lbl_surname=new JLabel();
         lbl_surname.setForeground(Color.WHITE);
         lbl_surname.setBounds(200,140,150,20);
         this.add(lbl_surname);
         lbl_surname.setFont(new Font("Tahoma",Font.BOLD,15));

         cb_roll_no.addItemListener(this);

         try 
         {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from student where roll_no = '" +cb_roll_no.getSelectedItem()+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
               lbl_name.setText(rs.getString("name"));
               lbl_surname.setText(rs.getString("sname"));
            }    
   
         }
         catch(Exception ex)
         {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION ",JOptionPane.ERROR_MESSAGE);
                      
         }

         lbl=new JLabel("Course");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(40,180,150,20);
         lbl.setFont(new Font("Tahoma",Font.BOLD,15));
         this.add(lbl);

         cb_course=new JComboBox<String>();
         cb_course.setBounds(200,180,150,20);
         cb_course.setBackground(Color.WHITE);
         this.add(cb_course);
         String arr1[]={"BTech","MTech","BCA","Bsc","Msc","MBA","MCA","MCom","BA","MA"};
         for(String item : arr1)
         {
             cb_course.addItem(item);
         }
         
         lbl=new JLabel("Branch");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(40,220,150,20);
         lbl.setFont(new Font("Tahoma",Font.BOLD,15));
         this.add(lbl);

         cb_branch=new JComboBox<String>();
         cb_branch.setBounds(200,220,150,20);
         cb_branch.setBackground(Color.WHITE);
         this.add(cb_branch);
         String arr2[]={"Computer Science","Mathematics","MicroBoilogy","Chemestry"};
         for(String item : arr2)
         {
            cb_branch.addItem(item);
         }

          lbl=new JLabel("Semester");
          lbl.setForeground(Color.WHITE);
          lbl.setFont(new Font("Tahoma",Font.BOLD,15));
          lbl.setBounds(40,260,150,20);
          this.add(lbl);

          jcb_student_Sem=new JComboBox<String>();
          jcb_student_Sem.setBounds(200,260,150,20);
          jcb_student_Sem.setBackground(Color.WHITE);
          this.add(jcb_student_Sem);

        String arr[]={"Semister1","Semister2","Semister3","Semister4","Semister5","Semister6","Semister7","Semister8"};
        for(String item:arr)
        {
          jcb_student_Sem.addItem(item); 
        }
        JLabel lbl_pay=new JLabel("Total Payable");
        lbl_pay.setForeground(Color.WHITE);
        lbl_pay.setForeground(Color.WHITE);
        lbl_pay.setFont(new Font("Tahoma",Font.BOLD,15));
        lbl_pay.setBounds(40,300,150,20);
        this.add(lbl_pay);

        lbl_total=new JLabel();
        lbl_total.setForeground(Color.WHITE);
        lbl_total.setFont(new Font("Tahoma",Font.BOLD,15));
        lbl_total.setBounds(200,300,150,20);
        this.add(lbl_total);

         btn=new JButton("UPDATE");
         btn.setBounds(30,380,100,25);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
         btn=new JButton("PAY FEE");
         btn.setBounds(150,380,100,25);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
         btn=new JButton("BACK");
         btn.setBounds(270,380,100,25);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);

       this.setSize(900,500);
       this.setLocation(300,100);
       this.setVisible(true);
       this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
         @Override
         public void actionPerformed(ActionEvent e)
         {
            String text = e.getActionCommand();
            switch(text)
           {
               case"UPDATE":
                String course = (String)cb_course.getSelectedItem();
                String semester = (String)jcb_student_Sem.getSelectedItem();
                try 
                {
                     Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                String sql = "select * from fee where course = '"+course+"'";
               PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                {
                    lbl_total.setText(rs.getString(semester));

                }
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
              
                }
                break;

            case"PAY FEE":
                String rollno = (String)cb_roll_no.getSelectedItem();
                String crs = (String)cb_course.getSelectedItem();
                String sem = (String)jcb_student_Sem.getSelectedItem();
                String branch = (String)cb_branch.getSelectedItem();
                String total = lbl_total.getText();
                 try 
                {

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                String sql = "insert into collegefee values('"+rollno+"','"+crs+"','"+branch+"','"+sem+"','"+total+"')";
                PreparedStatement ps =con.prepareStatement(sql);
                ps.executeUpdate();
          
                JOptionPane.showMessageDialog(this,"College fee submited..");
                this.dispose();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
              
                }
                break;

            case"BACK":
                this.dispose();
                break;
            }
         }
         @Override
         public void itemStateChanged(ItemEvent e)
         {
         try 
         {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from student where roll_no = '"+cb_roll_no.getSelectedItem()+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                lbl_name.setText(rs.getString("name"));
                lbl_surname.setText(rs.getString("sname"));
            }   
            con.close();
            rs.close();
            ps.close();
   
         }
         catch(Exception ex)
         {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
                      
         }
  }
}


class TeacherLeaveDetails extends JFrame implements ActionListener
{
    private JComboBox jcb_emp_id;
    private JTable tbl;
    private JScrollPane jsp;
    private JButton btn_search,btn_print,btn_cancel;
    private JLabel lbl;
    public TeacherLeaveDetails()
    {
       
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));
        
        lbl=new JLabel("Search By Employee_ID");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(20,20,150,20);
        this.add(lbl);

        jcb_emp_id=new JComboBox();
        jcb_emp_id.setBounds(180,20,150,20);
        this.add(jcb_emp_id);

         try 
         {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from teacherleave";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
             jcb_emp_id.addItem(rs.getString("emp_id"));   
           }

           rs.close();
           ps.close();
          con.close();

        }
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(this,ex);
        }
        
        tbl=new JTable();
         try 
        {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from teacherleave";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           tbl.setModel(DbUtils.resultSetToTableModel(rs));

           
           rs.close();
           ps.close();
           con.close();

        }
        catch(Exception ex)
        {
         JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
 
          
        }
        jsp=new JScrollPane(tbl);
        jsp.setBounds(0,100,900,600);
        this.add(jsp);

        btn_search=new JButton("Search");
        btn_search.setBounds(20,70,80,20);
        this.add(btn_search);
        btn_search.addActionListener(this);

        btn_print=new JButton("Print");
        btn_print.setBounds(120,70,80,20);
        this.add(btn_print);
        btn_print.addActionListener(this);


        btn_cancel=new JButton("Cancel");
        btn_cancel.setBounds(220,70,80,20);
        this.add(btn_cancel);
        btn_cancel.addActionListener(this);



        this.setVisible(true);
        this.setSize(900,700);
        this.setLocation(300,100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      String text=e.getActionCommand().toUpperCase();
      switch(text)
      {
      case"SEARCH":
        String sql = "select * from teacherleave where emp_id ='"+jcb_emp_id.getSelectedItem()+"'";    

        try 
        {
          DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");   
          PreparedStatement ps = con.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          tbl.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex)
        {
          JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
   
        }     

        break;
     
      case"CANCEL":
        this.setVisible(false);
        break;

      case"PRINT":
        try 
        {
         tbl.print();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
        
        }
        break;

      }
    }
}

class StudentLeaveDetails extends JFrame implements ActionListener
{


    private JComboBox jcb_rollno;
    private JTable tbl;
    private JScrollPane jsp;
    private JButton btn_search,btn_print,btn_cancel;
    private JLabel lbl;
    public StudentLeaveDetails()
    {
       
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));
        
        lbl=new JLabel("Search By Roll_No");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(20,20,150,20);
        this.add(lbl);

        jcb_rollno=new JComboBox();
        jcb_rollno.setBounds(180,20,150,20);
        this.add(jcb_rollno);

         try 
         {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from student";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
             jcb_rollno.addItem(rs.getString("roll_no"));   
           }

           rs.close();
           ps.close();
           con.close();

        }
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(this,ex);
        }
        
        tbl=new JTable();
         try 
        {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from studentleave";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
            tbl.setModel(DbUtils.resultSetToTableModel(rs));

           
           rs.close();
           ps.close();
           con.close();

        }
        catch(Exception ex)
        {
         JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
 
          
        }

        jsp=new JScrollPane(tbl);
        jsp.setBounds(0,100,900,600);
        this.add(jsp);

        btn_search=new JButton("Search");
        btn_search.setBounds(20,70,80,20);
        this.add(btn_search);
        btn_search.addActionListener(this);

        btn_print=new JButton("Print");
        btn_print.setBounds(120,70,80,20);
        this.add(btn_print);
        btn_print.addActionListener(this);


        btn_cancel=new JButton("Cancel");
        btn_cancel.setBounds(220,70,80,20);
        this.add(btn_cancel);
        btn_cancel.addActionListener(this);



        this.setVisible(true);
        this.setSize(900,700);
        this.setLocation(300,100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      String text=e.getActionCommand().toUpperCase();
      switch(text)
      {

        case"SEARCH":
       String sql = "select * from studentleave where roll_no ='"+jcb_rollno.getSelectedItem()+"'";    

        try 
        {
          DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");   
          PreparedStatement ps = con.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          tbl.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex)
        {
          JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
   
        }     

        break;
      
     
      case"CANCEL":
        this.setVisible(false);
        break;

      case"PRINT":
        try 
        {
         tbl.print();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
        
        }
        break;

      }
    }
    }

 
class StudentLeave extends JFrame implements ActionListener
{
  private JLabel lbl;
  private JComboBox jcb_student_leave;
  private JDateChooser dc_date;
  private JComboBox jcb_time;
  private JButton btn_submit,btn_cancel;
  public StudentLeave()
  {
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));
        

        lbl=new JLabel("Apply Student Leave ");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(40,50,300,30);
        lbl.setFont(new Font("Tahoma",Font.BOLD,20));
        this.add(lbl);

        lbl=new JLabel("Search By Roll_No");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(60,100,200,20);
        lbl.setFont(new Font("Tahoma",Font.BOLD,18));
        this.add(lbl);

        jcb_student_leave=new JComboBox();
        jcb_student_leave.setBounds(60,130,200,20);
        jcb_student_leave.setBackground(Color.WHITE);
        this.add(jcb_student_leave);
          try 
          {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from student";
             PreparedStatement ps= con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
             jcb_student_leave.addItem(rs.getString("roll_no"));   
           }
        }
        catch(Exception ex)
        {
        JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
        
        }

        lbl=new JLabel("Date");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(60,180,200,20);
        lbl.setFont(new Font("Tahoma",Font.BOLD,18));
        this.add(lbl);

        
        dc_date=new JDateChooser();
        dc_date.setBounds(60,210,200,25);
        this.add(dc_date);

        lbl=new JLabel("Time Duration");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(60,260,220,20);
        lbl.setFont(new Font("Tahoma",Font.BOLD,18));
        this.add(lbl);

        jcb_time=new JComboBox();
        jcb_time.setBounds(60,290,200,20);
        jcb_time.setBackground(Color.WHITE);
        jcb_time.addItem("Half Day ");
        jcb_time.addItem("Full Day");
        this.add(jcb_time);
      
        btn_submit=new JButton("SUBMIT");
        btn_submit.setBounds(60,350,100,25);
        btn_submit.setBackground(Color.BLACK);
        btn_submit.setForeground(Color.WHITE);
        btn_submit.addActionListener(this);
        btn_submit.setFont(new Font("Tahoma",Font.BOLD,15));
        this.add(btn_submit);



        btn_cancel=new JButton("CANCEL");
        btn_cancel.setBounds(200,350,100,25);
        btn_cancel.setBackground(Color.BLACK);
        btn_cancel.setForeground(Color.WHITE);
        btn_cancel.addActionListener(this);
        btn_cancel.setFont(new Font("Tahoma",Font.BOLD,15));
        this.add(btn_cancel);
 
    this.setVisible(true);
    this.setSize(500,550);
    this.setLocation(550,100);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
  @Override
  public void  actionPerformed(ActionEvent e)
  {
    String text=e.getActionCommand();
    switch(text)
    {
    case "SUBMIT":
      String roll_no=jcb_student_leave.getSelectedItem().toString();
      String date=((JTextField)dc_date.getDateEditor().getUiComponent()).getText();
      String duration=jcb_time.getSelectedItem().toString();

        try 
        {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
               Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
               String sql="insert into studentleave values('"+roll_no+"','"+date+"','"+duration+"')";
               PreparedStatement ps=con.prepareStatement(sql);
               int n=ps.executeUpdate(sql);
               if(n==1)
               {
                JOptionPane.showMessageDialog(this,"Leave Confirmed ");
               }
               else 
               {
                JOptionPane.showMessageDialog(this,"Leave not confirmed");
               }

            }
            catch(Exception ex)
            {
              JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
        
            }
      break;

      case"CANCEL":

              this.dispose();
              break;   
    }
  }
}
class TeacherLeave extends JFrame implements ActionListener
{
    private JLabel lbl;
    private JComboBox jcb_teacher_leave;
    private JDateChooser dc_date;
    private JComboBox jcb_time;
    private JButton btn_submit,btn_cancel;
    public TeacherLeave()
   {
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));

        lbl=new JLabel("Apply Teacher Leave ");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(40,50,300,30);
        lbl.setFont(new Font("Tahoma",Font.BOLD,20));
        this.add(lbl);

        lbl=new JLabel("Search By Emp Id");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(60,100,200,20);
        lbl.setFont(new Font("Tahoma",Font.BOLD,18));
        this.add(lbl);

        jcb_teacher_leave=new JComboBox();
        jcb_teacher_leave.setBounds(60,130,200,20);
        jcb_teacher_leave.setBackground(Color.WHITE);
        this.add(jcb_teacher_leave);
          try 
          {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from teacher";
             PreparedStatement ps= con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
             jcb_teacher_leave.addItem(rs.getString("emp_id"));   
           }
        }
        catch(Exception ex)
        {
        ex.printStackTrace();
        }

        lbl=new JLabel("Date");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(60,180,200,20);
        lbl.setFont(new Font("Tahoma",Font.BOLD,18));
        this.add(lbl);
  
        dc_date=new JDateChooser();
        dc_date.setBounds(60,210,200,25);
        this.add(dc_date);

        lbl=new JLabel("Time Duration");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(60,260,220,20);
        lbl.setFont(new Font("Tahoma",Font.BOLD,18));
        this.add(lbl);

        jcb_time=new JComboBox();
        jcb_time.setBounds(60,290,200,20);
        jcb_time.setBackground(Color.WHITE);
        jcb_time.addItem("Half Day ");
        jcb_time.addItem("Full Day");
        this.add(jcb_time);
      
        btn_submit=new JButton("SUBMIT");
        btn_submit.setBounds(60,350,100,25);
        btn_submit.setBackground(Color.BLACK);
        btn_submit.setForeground(Color.WHITE);
        btn_submit.addActionListener(this);
        btn_submit.setFont(new Font("Tahoma",Font.BOLD,15));
        this.add(btn_submit);

        btn_cancel=new JButton("CANCEL");
        btn_cancel.setBounds(200,350,100,25);
        btn_cancel.setBackground(Color.BLACK);
        btn_cancel.setForeground(Color.WHITE);
        btn_cancel.addActionListener(this);
        btn_cancel.setFont(new Font("Tahoma",Font.BOLD,15));
        this.add(btn_cancel);

    this.setVisible(true);
    this.setSize(500,550);
    this.setLocation(550,100);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
  @Override
  public void  actionPerformed(ActionEvent e)
  {
    String text=e.getActionCommand();
    switch(text)
    {
    case "SUBMIT":
      String emp_id=jcb_teacher_leave.getSelectedItem().toString();
      String date=((JTextField)dc_date.getDateEditor().getUiComponent()).getText();
      String duration=jcb_time.getSelectedItem().toString();

        try 
        {
           
 
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
               Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
               String sql="insert into teacherleave values('"+emp_id+"','"+date+"','"+duration+"')";
               PreparedStatement ps=con.prepareStatement(sql);
               int n=ps.executeUpdate(sql);
               if(n==1)
               {
                JOptionPane.showMessageDialog(this,"Leave Confirmed ");
               }
               else 
               {
                JOptionPane.showMessageDialog(this,"Leave not confirmed");
               }

            }
            catch(Exception ex)
            {
             JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
        
            }
           break;

      case "CANCEL":

              this.dispose();
              break;   
    }
  }
}


class UpdateTeacher extends JFrame implements ActionListener,ItemListener
{
    private JLabel lbl,lbl_name,lbl_emp_id,lbl_adhar_no,lbl_surname,lbl_ten_persent,lbl_twelve_persent,lbl_date_birth;
    private JTextField txt_address,txt_phone_no,txt_email_id,txt_qualification,txt_department;
    private JComboBox cb_emp_id;

    private JButton btn;
    public UpdateTeacher()
    {
         this.setLayout(null);
         this.getContentPane().setBackground(new Color(255-102-102));
        
         lbl=new JLabel("Update Teacher Details");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(310,30,500,50);
         lbl.setFont(new Font("serif",Font.BOLD,30));
         this.add(lbl);

         lbl=new JLabel("Select Employee_Id");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,100,200,20);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         cb_emp_id=new JComboBox();
         cb_emp_id.setBackground(Color.WHITE);
         cb_emp_id.setBounds(250,100,150,20);
         this.add(cb_emp_id);

         try
         {
                
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
              String sql="select * from teacher";
                 
                   PreparedStatement ps=con.prepareStatement(sql);
                   ResultSet rs= ps.executeQuery();
                   while(rs.next())
                   {
                     cb_emp_id.addItem(rs.getString("emp_id"));
                   }

                  
         }
         catch(Exception ex)
         {
           JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
         }
    
        
        
         lbl=new JLabel("Name");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,150,100,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         lbl_name=new JLabel();
         lbl_name.setForeground(Color.WHITE);
         lbl_name.setBounds(200,150,150,30);
         this.add(lbl_name);
         lbl_name.setFont(new Font("serif",Font.PLAIN,15));

         lbl=new JLabel("Surame");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,150,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         lbl_surname=new JLabel();
         lbl_surname.setForeground(Color.WHITE);
         lbl_surname.setBounds(600,150,150,30);
         this.add(lbl_surname);
         lbl_surname.setFont(new Font("serif",Font.PLAIN,15));

         lbl=new JLabel("Employee_ID");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         lbl_emp_id=new JLabel();
         lbl_emp_id.setForeground(Color.WHITE);
         lbl_emp_id.setBounds(200,200,200,30);
         this.add(lbl_emp_id);
         lbl_emp_id.setFont(new Font("serif",Font.PLAIN,15));

         lbl=new JLabel("Date of Birth");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         lbl_date_birth=new JLabel();
         lbl_date_birth.setForeground(Color.WHITE);
         lbl_date_birth.setBounds(600,200,150,30);
         this.add(lbl_date_birth);
         lbl_date_birth.setFont(new Font("serif",Font.PLAIN,15));

         

         lbl=new JLabel("Address");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_address=new JTextField();
          txt_address.setBounds(200,250,150,30);
         this.add(txt_address);
         txt_address.setFont(new Font("serif",Font.PLAIN,15));



         lbl=new JLabel("Phone Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_phone_no=new JTextField();
         txt_phone_no.setBounds(600,250,150,30);
         this.add(txt_phone_no);
         txt_phone_no.setFont(new Font("serif",Font.PLAIN,15));


         lbl=new JLabel("Email ID");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_email_id=new JTextField();
         txt_email_id.setBounds(200,300,150,30);
         this.add(txt_email_id);

         lbl=new JLabel("Class X(%)");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);


        lbl_ten_persent=new JLabel();
        lbl_ten_persent.setForeground(Color.WHITE);
         lbl_ten_persent.setBounds(600,300,150,30);
         this.add(lbl_ten_persent);
         lbl_ten_persent.setFont(new Font("serif",Font.PLAIN,15));

          
        lbl=new JLabel("Class XII(%)");
        lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

        lbl_twelve_persent=new JLabel();
        lbl_twelve_persent.setForeground(Color.WHITE);
         lbl_twelve_persent.setBounds(200,350,150,30);
         this.add(lbl_twelve_persent);
         lbl_twelve_persent.setFont(new Font("serif",Font.PLAIN,15));


         lbl=new JLabel("Aadhar Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

        lbl_adhar_no=new JLabel();
        lbl_adhar_no.setForeground(Color.WHITE);
         lbl_adhar_no.setBounds(600,350,200,30);
         this.add(lbl_adhar_no);
         lbl_adhar_no.setFont(new Font("serif",Font.PLAIN,15));


         lbl=new JLabel("Education");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_qualification=new JTextField();
         txt_qualification.setBounds(200,400,150,30);
         this.add(txt_qualification);
         txt_qualification.setFont(new Font("serif",Font.PLAIN,15));

         
         lbl=new JLabel("Department");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

          txt_department=new JTextField();
         txt_department.setForeground(Color.WHITE);
         txt_department.setBounds(600,400,150,30);
         this.add(txt_department);
         txt_department.setFont(new Font("serif",Font.PLAIN,15));

         cb_emp_id.addItemListener(this);


         btn=new JButton("UPDATE");
         btn.setBounds(250,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
         btn=new JButton("CANCEL");
         btn.setBounds(450,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
    
        this.setVisible(true);
        this.setSize(900,650);
        this.setLocation(350,50);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    
        public void itemStateChanged(ItemEvent e)
         {
         try 
         {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from teacher where emp_id = "+cb_emp_id.getSelectedItem()+"";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                
                lbl_surname.setText(rs.getString("sname"));
                lbl_name.setText(rs.getString("name"));
                lbl_date_birth.setText(rs.getString("dob"));
                lbl_ten_persent.setText(rs.getString("class_x"));
                lbl_twelve_persent.setText(rs.getString("class_xii"));
                lbl_emp_id.setText(rs.getString("emp_id"));
                txt_address.setText(rs.getString("address"));
                txt_phone_no.setText(rs.getString("phone"));
                txt_email_id.setText(rs.getString("email"));
                lbl_adhar_no.setText(rs.getString("aadhar"));
                 txt_qualification.setText(rs.getString("qualification"));
                txt_department.setText(rs.getString("department"));
            }   
            con.close();
            rs.close();
            ps.close();
   
         }
         catch(Exception ex)
         {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
                      
         }

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
     
        String text=e.getActionCommand();
       switch(text)
       {
            case"UPDATE":
            String empid= lbl_emp_id.getText();
            String address = txt_address.getText();
            String phone = txt_phone_no.getText();
            String email_id= txt_email_id.getText();
            String qualification = txt_qualification.getText();
            String department = txt_department.getText();

             if(empid.isEmpty()==true||address.isEmpty()||phone.isEmpty()||email_id.isEmpty()||qualification.isEmpty()||department.isEmpty())
            {
                JOptionPane.showMessageDialog(this,"Fill up  the form properly..!");
            }
            else 

                 lbl_name.setText("");
                lbl_surname.setText("");
                lbl_date_birth.setText("");
                lbl_ten_persent.setText("");
                lbl_twelve_persent.setText("");
                lbl_emp_id.setText("");
                txt_address.setText("");
                txt_phone_no.setText("");
                txt_email_id.setText("");
                lbl_adhar_no.setText("");
                 txt_department.setText("");
                txt_qualification.setText("");

            try
            {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                  String sql="update teacher set address='"+address+"',phone='"+phone+"',email='"+email_id+"',qualification='"+qualification+"',department='"+department+"'where emp_id='"+empid+"'";

               
                  PreparedStatement ps=con.prepareStatement(sql);
                  ps.executeUpdate();

                  JOptionPane.showMessageDialog(this,"Teacher Details updated successfully","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                   
                  
                  ps.close();
                  con.close();

            }
            catch(Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Exception for database conn:"+ex.toString());
 
            }

            break;
        
           case"CANCEL":
            this.dispose();
            break;

         }       
    
    }

 }
class UpdateStudent extends JFrame implements ActionListener,ItemListener
{
    private JLabel lbl,lbl_name,lbl_roll_no,lbl_adhar_no,lbl_surname,lbl_ten_persent,lbl_twelve_persent,lbl_date_birth;
    private JTextField txt_address,txt_phone_no,txt_email_id,txt_course,txt_branch;
    private JComboBox cb_roll_no;

    private JButton btn;
    public UpdateStudent()
    {
         this.setLayout(null);
         this.getContentPane().setBackground(new Color(255-102-102));
        
         lbl=new JLabel("Update Students Details");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(310,30,500,50);
         lbl.setFont(new Font("serif",Font.BOLD,30));
         this.add(lbl);

         lbl=new JLabel("Select Roll No");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,100,200,20);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         cb_roll_no=new JComboBox();
         cb_roll_no.setBackground(Color.WHITE);
         cb_roll_no.setBounds(200,100,150,20);
         this.add(cb_roll_no);

         try
          {
                
               Class.forName("com.mysql.cj.jdbc.Driver");
               Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
               String sql="select * from student";
               
                  PreparedStatement ps=con.prepareStatement(sql);
                  ResultSet rs= ps.executeQuery();
                   while(rs.next())
                   {
                     cb_roll_no.addItem(rs.getString("roll_no"));
                   }

                  
         }
         catch(Exception ex)
         {
           JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
         }
     
         lbl=new JLabel("Name");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,150,100,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

        lbl_name=new JLabel();
        lbl_name.setForeground(Color.WHITE);
         lbl_name.setBounds(200,150,150,30);
         this.add(lbl_name);
         lbl_name.setFont(new Font("serif",Font.PLAIN,15));

         lbl=new JLabel("Surname");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,150,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

        lbl_surname=new JLabel();
        lbl_surname.setForeground(Color.WHITE);
         lbl_surname.setBounds(600,150,150,30);
         this.add(lbl_surname);
         lbl_surname.setFont(new Font("serif",Font.PLAIN,15));

         lbl=new JLabel("Roll_No");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         lbl_roll_no=new JLabel();
         lbl_roll_no.setForeground(Color.WHITE);
         lbl_roll_no.setBounds(200,200,200,30);
         this.add(lbl_roll_no);
         lbl_roll_no.setFont(new Font("serif",Font.PLAIN,15));

         lbl=new JLabel("Date of Birth");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

          lbl_date_birth=new JLabel();
          lbl_date_birth.setForeground(Color.WHITE);
         lbl_date_birth.setBounds(600,200,150,30);
         this.add(lbl_date_birth);
         lbl_date_birth.setFont(new Font("serif",Font.PLAIN,15));

         

         lbl=new JLabel("Address");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_address=new JTextField();
         txt_address.setBounds(200,250,150,30);
         this.add(txt_address);
         txt_address.setFont(new Font("serif",Font.PLAIN,15));



         lbl=new JLabel("Phone Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_phone_no=new JTextField();
         txt_phone_no.setBounds(600,250,150,30);
         this.add(txt_phone_no);
         txt_phone_no.setFont(new Font("serif",Font.PLAIN,15));


         lbl=new JLabel("Email ID");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_email_id=new JTextField();
         txt_email_id.setBounds(200,300,150,30);
         this.add(txt_email_id);

         lbl=new JLabel("Class X(%)");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);


        lbl_ten_persent=new JLabel();
        lbl_ten_persent.setForeground(Color.WHITE);
         lbl_ten_persent.setBounds(600,300,150,30);
         this.add(lbl_ten_persent);
         lbl_ten_persent.setFont(new Font("serif",Font.PLAIN,15));

          
        lbl=new JLabel("Class XII(%)");
        lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

        lbl_twelve_persent=new JLabel();
        lbl_twelve_persent.setForeground(Color.WHITE);
         lbl_twelve_persent.setBounds(200,350,150,30);
         this.add(lbl_twelve_persent);
         lbl_twelve_persent.setFont(new Font("serif",Font.PLAIN,15));


         lbl=new JLabel("Aadhar Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         lbl_adhar_no=new JLabel();
         lbl_adhar_no.setForeground(Color.WHITE);
         lbl_adhar_no.setBounds(600,350,200,30);
         this.add(lbl_adhar_no);
         lbl_adhar_no.setFont(new Font("serif",Font.PLAIN,15));


         lbl=new JLabel("Course");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_course=new JTextField();
         txt_course.setBounds(200,400,150,30);
         this.add(txt_course);
         txt_course.setFont(new Font("serif",Font.PLAIN,15));

         
         lbl=new JLabel("Branch");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_branch=new JTextField();
         txt_branch.setBounds(600,400,150,30);
         this.add(txt_branch);
         txt_branch.setFont(new Font("serif",Font.PLAIN,15));
         cb_roll_no.addItemListener(this);


 
         btn=new JButton("UPDATE");
         btn.setBounds(250,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
         btn=new JButton("CANCEL");
         btn.setBounds(450,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
    
        this.setVisible(true);
        this.setSize(900,650);
        this.setLocation(350,50);
    
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    @Override
    public void itemStateChanged(ItemEvent e)
    {
         try 
         {
             
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from student where roll_no = '"+cb_roll_no.getSelectedItem()+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                
                lbl_surname.setText(rs.getString("sname"));
                lbl_name.setText(rs.getString("name"));
                lbl_date_birth.setText(rs.getString("dob"));
                lbl_ten_persent.setText(rs.getString("class_x"));
                lbl_twelve_persent.setText(rs.getString("class_xii"));
                lbl_roll_no.setText(rs.getString("roll_no"));
                txt_address.setText(rs.getString("address"));
                txt_phone_no.setText(rs.getString("phone"));
                txt_email_id.setText(rs.getString("email"));
                lbl_adhar_no.setText(rs.getString("aadhar"));
                 txt_branch.setText(rs.getString("branch"));
                txt_course.setText(rs.getString("course"));
               
            }   
            con.close();
            rs.close();
            ps.close();
   
         }
         catch(Exception ex)
         {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
                      
         }

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
     
        String text=e.getActionCommand();
       switch(text)
       {
            case"UPDATE":
            String rollno = lbl_roll_no.getText();
            String address = txt_address.getText();
            String phone = txt_phone_no.getText();
            String email_id= txt_email_id.getText();
            String branch = txt_branch.getText();
            String course = txt_course.getText();

            if(rollno.isEmpty()==true||address.isEmpty()||phone.isEmpty()||email_id.isEmpty()||branch.isEmpty()||course.isEmpty())
            {
                JOptionPane.showMessageDialog(this,"Fill up  the form properly..!");
            }
            else 
            {

               lbl_name.setText("");
                lbl_surname.setText("");
                lbl_date_birth.setText("");
                lbl_ten_persent.setText("");
                lbl_twelve_persent.setText("");
                lbl_roll_no.setText("");
                txt_address.setText("");
                txt_phone_no.setText("");
                txt_email_id.setText("");
                lbl_adhar_no.setText("");
                 txt_branch.setText("");
                txt_course.setText("");

             try
            {
                  Class.forName("com.mysql.cj.jdbc.Driver");
                  Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                  String sql="update student set address='"+address+"',phone='"+phone+"',email='"+email_id+"',course='"+course+"',branch='"+branch+"'where roll_no='"+rollno+"'";

               
                  PreparedStatement ps=con.prepareStatement(sql);
                  ps.executeUpdate();

                  JOptionPane.showMessageDialog(this,"Student Details updated successfully","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                   
                   ps.close();
                  con.close();

            }
            catch(Exception ex)
            {
                 JOptionPane.showMessageDialog(this,"Exception for database conn:"+ex.toString());
 
            }
        }

            break;
        
           case"CANCEL":
            this.dispose();
            break;

         }       
    
    }

 }
class TeacherDetails extends JFrame implements ActionListener
{
     private JLabel lbl;
     private JComboBox jcb;
     private JTable tbl;
     private JScrollPane jsp;
     private JButton btn_search,btn_print,btn_add,btn_update,btn_cancel;

    public TeacherDetails()
    { 
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));
        
        lbl=new JLabel("Search By Employee_ID");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(20,20,150,20);
        this.add(lbl);

        jcb=new JComboBox();
        jcb.setBounds(180,20,150,20);
        this.add(jcb);

        try 
        {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from teacher";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
             jcb.addItem(rs.getString("emp_id"));   
           }
           rs.close();
           ps.close();
           con.close();

        }
        catch(Exception ex)
        {
         JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE); 
        }
        
        tbl=new JTable();
         try 
        {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
            String sql = "select * from teacher";
            PreparedStatement ps= con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            tbl.setModel(DbUtils.resultSetToTableModel(rs));

           
           rs.close();
           ps.close();
           con.close();

        }
        catch(Exception ex)
        {
         JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);

        }
        jsp=new JScrollPane(tbl);
        jsp.setBounds(0,100,900,600);
        this.add(jsp);

        btn_search=new JButton("Search");
        btn_search.setBounds(20,70,80,20);
        this.add(btn_search);
        btn_search.addActionListener(this);

        btn_print=new JButton("Print");
        btn_print.setBounds(120,70,80,20);
        this.add(btn_print);
        btn_print.addActionListener(this);

        btn_add=new JButton("Add");
        btn_add.setBounds(220,70,80,20);
        this.add(btn_add);
        btn_add.addActionListener(this);

        btn_update=new JButton("Update");
        btn_update.setBounds(320,70,80,20);
        this.add(btn_update);
        btn_update.addActionListener(this);

        btn_cancel=new JButton("Cancel");
        btn_cancel.setBounds(420,70,80,20);
        this.add(btn_cancel);
        btn_cancel.addActionListener(this);

        this.setVisible(true);
        this.setSize(900,700);
        this.setLocation(300,100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
      String text=e.getActionCommand().toUpperCase();
      switch(text)
      {
      case"SEARCH":
       String sql = "select * from teacher where emp_id ='"+jcb.getSelectedItem()+"'";    

        try 
        {
          DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");   
          PreparedStatement ps = con.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          tbl.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex)
        {
          JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
   
        }
        break;
      
      case"ADD":
         this.setVisible(false);
         new AddTeacher();
         break;

      case"UPDATE":
         this.setVisible(false);
         new UpdateTeacher();
         break;

      case"CANCEL":
         this.setVisible(false);
         break;

      case"PRINT":
        try 
        {
         tbl.print();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        break;

      }
    }
}

class StudentDetails extends JFrame  implements ActionListener
{
     
   private JLabel lbl;
   private JComboBox jcb;
   private JTable tbl;
   private JScrollPane jsp;
   private JButton btn_search,btn_print,btn_add,btn_update,btn_cancel;


    public StudentDetails()
    { 
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(255-102-102));
        
        lbl=new JLabel("Search By Roll_No");
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(20,20,150,20);
        this.add(lbl);

        jcb=new JComboBox();
        jcb.setBounds(180,20,150,20);
        this.add(jcb);

        try 
        {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from student";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           while(rs.next())
           {
             jcb.addItem(rs.getString("roll_no"));   
           }

           rs.close();
           ps.close();
          con.close();

        }
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(this,ex);
        }
        
        tbl=new JTable();
        try 
        {
           DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
           String sql = "select * from student";
           PreparedStatement ps= con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
            tbl.setModel(DbUtils.resultSetToTableModel(rs));

           rs.close();
           ps.close();
         con.close();

        }
        catch(Exception ex)
        {
           JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);   
        }
        
        jsp=new JScrollPane(tbl);
        jsp.setBounds(0,100,900,600);
        this.add(jsp);

        btn_search=new JButton("Search");
        btn_search.setBounds(20,70,80,20);
        this.add(btn_search);
        btn_search.addActionListener(this);

        btn_print=new JButton("Print");
        btn_print.setBounds(120,70,80,20);
        this.add(btn_print);
        btn_print.addActionListener(this);

        btn_add=new JButton("Add");
        btn_add.setBounds(220,70,80,20);
        this.add(btn_add);
        btn_add.addActionListener(this);

        btn_update=new JButton("Update");
        btn_update.setBounds(320,70,80,20);
        this.add(btn_update);
        btn_update.addActionListener(this);

        btn_cancel=new JButton("Cancel");
        btn_cancel.setBounds(420,70,80,20);
        this.add(btn_cancel);
        btn_cancel.addActionListener(this);

        this.setVisible(true);
        this.setSize(900,700);
        this.setLocation(300,100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
      String text=e.getActionCommand().toUpperCase();
      switch(text)
      {
      case"SEARCH":
        String sql = "select * from student where roll_no ='"+jcb.getSelectedItem()+"'";
         

        try 
        {
          DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");   
          PreparedStatement ps = con.prepareStatement(sql);
          ResultSet rs = ps.executeQuery();
          tbl.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex)
        {
          JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
   
        }
        break;

      case"ADD":
         this.setVisible(false);
         new AddStudent();
         break;

      case"UPDATE":
          this.setVisible(false);
          new UpdateStudent();
          break;

      case"CANCEL":
          this.setVisible(false);
          break;

      case"PRINT":
        try 
        {
         tbl.print();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
 
        }
        break;

      }
    }
}
class AddStudent extends JFrame implements ActionListener
{ 
    private JLabel lbl;
    private JTextField txt_name,txt_surname,txt_roll_no,txt_address,txt_phone_no,txt_email_id;
    private JTextField txt_ten_persent,txt_twelve_persent,txt_adhar_no;
    private JDateChooser date_birth;
    private JComboBox <String>cb_course;
    private JComboBox <String> cb_branch;
    private JButton btn;

    public AddStudent()
    {
         this.setLayout(null);
         this.getContentPane().setBackground(new Color(255-102-102));
         lbl=new JLabel("New Students Details");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(310,30,500,50);
         lbl.setFont(new Font("serif",Font.BOLD,30));
         this.add(lbl);

         lbl=new JLabel("Name");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,150,100,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_name=new JTextField();
         txt_name.setBounds(200,150,150,30);
         this.add(txt_name);

         lbl=new JLabel("Surname");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,150,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_surname=new JTextField();
         txt_surname.setBounds(600,150,150,30);
         this.add(txt_surname);

         lbl=new JLabel("Roll_No");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_roll_no=new JTextField();
         txt_roll_no.setBounds(200,200,150,30);
         this.add(txt_roll_no);

         lbl=new JLabel("Date of Birth");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         date_birth=new JDateChooser();
         date_birth.setBounds(600,200,150,30);
         this.add(date_birth);
         

         lbl=new JLabel("Address");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_address=new JTextField();
         txt_address.setBounds(200,250,150,30);
         this.add(txt_address);


         lbl=new JLabel("Phone Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_phone_no=new JTextField();
         txt_phone_no.setBounds(600,250,150,30);
         this.add(txt_phone_no);

         lbl=new JLabel("Email ID");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_email_id=new JTextField();
         txt_email_id.setBounds(200,300,150,30);
         this.add(txt_email_id);

         lbl=new JLabel("Class X(%)");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_ten_persent=new JTextField();
         txt_ten_persent.setBounds(600,300,150,30);
         this.add(txt_ten_persent);
          
        lbl=new JLabel("Class XII(%)");
        lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_twelve_persent=new JTextField();
         txt_twelve_persent.setBounds(200,350,150,30);
         this.add(txt_twelve_persent);

         lbl=new JLabel("Aadhar Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_adhar_no=new JTextField();
         txt_adhar_no.setBounds(600,350,150,30);
         this.add(txt_adhar_no);

         lbl=new JLabel("Course");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         cb_course=new JComboBox<String>();
         cb_course.setBounds(200,400,150,30);
         cb_course.setBackground(Color.WHITE);
         this.add(cb_course);
         String arr1[]={"BTech","MTech","BCA","Bsc","Msc","MBA","MCA","MCom","BA","MA"};
         for(String item : arr1)
         {
             cb_course.addItem(item);
         }
         
         lbl=new JLabel("Branch");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         cb_branch=new JComboBox<String>();
         cb_branch.setBounds(600,400,150,30);
         cb_branch.setBackground(Color.WHITE);
         this.add(cb_branch);
         String arr2[]={"Computer Science","Mathematics","MicroBoilogy","Chemestry"};
         for(String item : arr2)
         {
            cb_branch.addItem(item);
         }

         btn=new JButton("SUBMIT");
         btn.setBounds(250,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
         btn=new JButton("CANCEL");
         btn.setBounds(450,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);

     
        this.setVisible(true);
        this.setSize(900,700);
        this.setLocation(350,50);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
     
      String text=e.getActionCommand();
       switch(text)
       {
            case"SUBMIT":
                    
            String name=txt_name.getText().toUpperCase();
            String surname=txt_surname.getText().toUpperCase();
            String roll_no=txt_roll_no.getText();
            String db= ((JTextField)date_birth.getDateEditor().getUiComponent()).getText();

            String address=txt_address.getText();
            String phone_no=txt_phone_no.getText();
            
            String email_id=txt_email_id.getText();
            String ten_persent=txt_ten_persent.getText();
            String twelve_persent=txt_twelve_persent.getText();
            String adhar_no=txt_adhar_no.getText();
            String course=cb_course.getSelectedItem().toString();
            String branch=cb_branch.getSelectedItem().toString();

            if(name.isEmpty()==true||surname.isEmpty()==true||roll_no.isEmpty()==true||address.isEmpty()==true||phone_no.isEmpty()==true||email_id.isEmpty()==true||ten_persent.isEmpty()==true||twelve_persent.isEmpty()==true||db.isEmpty()==true)
            {
              JOptionPane.showMessageDialog(this,"Fill up the form properly..!");
             }
            else 
             {

                if((phone_no.matches("^[0-9]*$")&& phone_no.length()==10))
              {

              
                  if((name.matches("^[A-Z]*$"))&&(surname.matches("^[A-Z]*$")))
                  {

                     if((adhar_no.matches("^[0-9]*$")&& adhar_no.length()==12))
                     {

                                   txt_name.setText("");
                                   txt_surname.setText("");
                                   txt_roll_no.setText("");
                                   date_birth.setCalendar(null);

         
                                   txt_address.setText("");
                                   txt_phone_no.setText("");
                                   txt_email_id.setText("");
                                    txt_ten_persent.setText("");
                                   txt_twelve_persent.setText("");
                                   txt_adhar_no.setText("");

                                    try
                                    {
                                        Class.forName("com.mysql.cj.jdbc.Driver");
                                        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                                        String sql="insert into student values('"+name+"','"+surname+"','"+roll_no+"','"+db+"','"+address+"','"+phone_no+"','"+email_id+"','"+ten_persent+"','"+twelve_persent+"','"+adhar_no+"','"+course+"','"+branch+"')";
               
                                              PreparedStatement ps=con.prepareStatement(sql);
                                              int n=ps.executeUpdate();
                                              ps.close();
                                             con.close();
                                            if(n==1)
                                            {
                                                JOptionPane.showMessageDialog(this,"Students Details inserted Successfully..","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                          else
                                            {
                                                  JOptionPane.showMessageDialog(this,"Students Details not inserted..","ERROR",JOptionPane.ERROR_MESSAGE);
                                             }

                                      }
                                      catch(Exception ex)
                                      {
                                             JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
 
                                      }
                            
                }
                else
                     JOptionPane.showMessageDialog(this,"INVALID AADHAR NUMBER");
               }
               else
                  JOptionPane.showMessageDialog(this,"Name should be Alphabet");
               }
               else

                  JOptionPane.showMessageDialog(this,"INVALID NUMBER");
                }
              break;
            
           case"CANCEL":
            this.dispose();
            break;
         }       
     }

}

class AddTeacher extends JFrame implements ActionListener
{ 
    private JLabel lbl;
    private JTextField txt_name,txt_surname,txt_emp_id,txt_address,txt_phone_no,txt_email_id;
    private JTextField txt_ten_persent,txt_twelve_persent,txt_adhar_no;
    private JDateChooser date_birth;
    private JComboBox<String>cb_qualification;
    private JComboBox<String>cb_dept;
    private JButton btn;

    public AddTeacher()
    {
         this.setLayout(null);
         this.getContentPane().setBackground(new Color(255-102-102));
         lbl=new JLabel("New Teacher Details");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(310,30,500,50);
         lbl.setFont(new Font("serif",Font.BOLD,30));
         this.add(lbl);

         lbl=new JLabel("Name");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,150,100,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_name=new JTextField();
         txt_name.setBounds(200,150,150,30);
         this.add(txt_name);

         lbl=new JLabel("Surname");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,150,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_surname=new JTextField();
         txt_surname.setBounds(600,150,150,30);
         this.add(txt_surname);

         lbl=new JLabel("Employee_ID");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_emp_id=new JTextField();
         txt_emp_id.setBounds(200,200,150,30);
         this.add(txt_emp_id);

         lbl=new JLabel("Date of Birth");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,200,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         date_birth=new JDateChooser();
         date_birth.setBounds(600,200,150,30);
         this.add(date_birth);
         

         lbl=new JLabel("Address");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_address=new JTextField();
         txt_address.setBounds(200,250,150,30);
         this.add(txt_address);


         lbl=new JLabel("Phone Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,250,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_phone_no=new JTextField();
         txt_phone_no.setBounds(600,250,150,30);
         this.add(txt_phone_no);

         lbl=new JLabel("Email ID");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_email_id=new JTextField();
         txt_email_id.setBounds(200,300,150,30);
         this.add(txt_email_id);

         lbl=new JLabel("Class X(%)");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,300,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_ten_persent=new JTextField();
         txt_ten_persent.setBounds(600,300,150,30);
         this.add(txt_ten_persent);
          
         lbl=new JLabel("Class XII(%)");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_twelve_persent=new JTextField();
         txt_twelve_persent.setBounds(200,350,150,30);
         this.add(txt_twelve_persent);

         lbl=new JLabel("Aadhar Number");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,350,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         txt_adhar_no=new JTextField();
         txt_adhar_no.setBounds(600,350,150,30);
         this.add(txt_adhar_no);

         lbl=new JLabel("Qualification");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(50,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         cb_qualification=new JComboBox<String>();
         cb_qualification.setBounds(200,400,150,30);
         cb_qualification.setBackground(Color.WHITE);
         this.add(cb_qualification);
         String arr1[]={"BTech","MTech","BCA","Bsc","Msc","MBA","MCA","MCom","BA","MA"};
         for(String item : arr1)
         {
            cb_qualification.addItem(item);
         }
         
         lbl=new JLabel("Department");
         lbl.setForeground(Color.WHITE);
         lbl.setBounds(400,400,200,30);
         lbl.setFont(new Font("serif",Font.BOLD,20));
         this.add(lbl);

         cb_dept=new JComboBox<String>();
         cb_dept.setBounds(600,400,150,30);
         cb_dept.setBackground(Color.WHITE);
         this.add(cb_dept);
         String arr2[]={"Computer Science","Mathematics","MicroBoilogy","Chemestry"};
         for(String item : arr2)
         {
            cb_dept.addItem(item);
         }

         btn=new JButton("SUBMIT");
         btn.setBounds(250,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
         btn=new JButton("CANCEL");
         btn.setBounds(450,550,120,30);
         btn.setBackground(Color.BLACK);
         btn.setForeground(Color.WHITE);
         btn.setFont(new Font("Tahoma",Font.BOLD,15));
         btn.addActionListener(this);
         this.add(btn);
         
    
        this.setVisible(true);
        this.setSize(900,700);
        this.setLocation(350,50);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
     
      String text=e.getActionCommand();
       switch(text)
       {
            case"SUBMIT":

            String name=txt_name.getText().toUpperCase();
            String surname=txt_surname.getText().toUpperCase();
            String emp_id=txt_emp_id.getText();
            String db= ((JTextField)date_birth.getDateEditor().getUiComponent()).getText();
            String address=txt_address.getText();
            String phone_no=txt_phone_no.getText();
            String email_id=txt_email_id.getText();
            String ten_persent=txt_ten_persent.getText();
            String twelve_persent=txt_twelve_persent.getText();
            String adhar_no=txt_adhar_no.getText();
            String qualification=cb_qualification.getSelectedItem().toString();
            String dept=cb_dept.getSelectedItem().toString();

            if(name.isEmpty()==true||surname.isEmpty()==true||emp_id.isEmpty()==true||address.isEmpty()==true||phone_no.isEmpty()==true||email_id.isEmpty()==true||ten_persent.isEmpty()==true||twelve_persent.isEmpty()==true||db.isEmpty()==true)
             {
              JOptionPane.showMessageDialog(this,"Fill up the form properly..!");
             }
    
              else 
              {
              if((phone_no.matches("^[0-9]*$")&& phone_no.length()==10))
               {
                  if((name.matches("^[A-Z]*$"))&&(surname.matches("^[A-Z]*$")))
                  {

                     if((adhar_no.matches("^[0-9]*$")&& adhar_no.length()==12))
                     {

                       txt_name.setText("");
                       txt_surname.setText("");
                       txt_emp_id.setText("");
                       date_birth.setCalendar(null);

                       txt_address.setText("");
                       txt_phone_no.setText("");
                       txt_email_id.setText("");
                       txt_ten_persent.setText("");
                       txt_twelve_persent.setText("");
                       txt_adhar_no.setText("");


                       try
                       {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                       Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/universitymanagementdb","root","super");
                       String sql="insert into teacher values('"+name+"','"+surname+"','"+emp_id+"','"+db+"','"+address+"','"+phone_no+"','"+email_id+"','"+ten_persent+"','"+twelve_persent+"','"+adhar_no+"','"+qualification+"','"+dept+"')";
               
                       PreparedStatement ps=con.prepareStatement(sql);
                       int n=ps.executeUpdate();
                       ps.close();
                       con.close();
                       if(n==1)
                       {
                         JOptionPane.showMessageDialog(this,"Teacher Details inserted Successfully..","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                        }
                       else
                       {
                          JOptionPane.showMessageDialog(this,"Teacher Details not inserted..","ERROR",JOptionPane.ERROR_MESSAGE);
                       }
   
                      }
                 catch(Exception ex)
                {
                 JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
 
                }
               }
           


                else
                     JOptionPane.showMessageDialog(this,"INVALID AADHAR NUMBER");
               }
               else
                  JOptionPane.showMessageDialog(this,"Name should be Alphabet");
               }
               else

                  JOptionPane.showMessageDialog(this,"INVALID NUMBER");
          }

            break;
            

           case "CANCEL":
            this.dispose();
            break;
         }       
    
    }

}

class MainFrame extends JFrame implements ActionListener
{
        private JLabel lbl;
        private JMenuBar mb;
        private JButton b;
        private JSplitPane jsp;
        private JPanel p;
        private JMenu m_NewInformation,m_ViewDetails,m_ApplyLeave,m_LeaveDetails,m_Examination,m_UpdateDetails;
        private JMenu m_FeeDetails,m_Utility,m_About,m_Exit;
        private JMenuItem mi;      
        public MainFrame()
        {
          

           this.setTitle("Institute ManageMent System");
           mb = new JMenuBar();
           mb.setLayout(new GridLayout(1,10));
           this.add(mb,BorderLayout.NORTH);
           
           m_NewInformation = new JMenu("NewInformation");
           m_NewInformation.setForeground(Color.RED);
           mb.add(m_NewInformation);
           String arg1[] ={"New Faculty Information","New Student Information"};
           for(int i =0;i<arg1.length;i++)
            {
              mi=new JMenuItem(arg1[i]);
              m_NewInformation.add(mi);
              mi.addActionListener(this);
            }

           m_ViewDetails = new JMenu("View Details");
           m_ViewDetails.setForeground(Color.BLUE);
           mb.add(m_ViewDetails);
           String arg2[] ={"View Student Details","View Teacher Details"};
           for(int i =0;i<arg2.length;i++)
            {
              mi=new JMenuItem(arg2[i]);
              m_ViewDetails.add(mi);
              mi.addActionListener(this);
            }
           m_ApplyLeave = new JMenu("Apply Leave");
           m_ApplyLeave.setForeground(Color.RED);
           mb.add(m_ApplyLeave);
           String arg3[] ={"Student Leave","Teacher Leave"};
           for(int i =0;i<arg3.length;i++)
            {
              mi=new JMenuItem(arg3[i]);
              m_ApplyLeave.add(mi);
              mi.addActionListener(this);
            }

           m_LeaveDetails = new JMenu("Leave Details");
           m_LeaveDetails.setForeground(Color.BLUE);
           mb.add(m_LeaveDetails);
           String arg4[] ={"Student Leave Details","Teacher Leave Details"};
           for(int i =0;i<arg4.length;i++)
            {
              mi=new JMenuItem(arg4[i]);
              m_LeaveDetails.add(mi);
              mi.addActionListener(this);
            }
           m_Examination = new JMenu("Examination");
           m_Examination.setForeground(Color.RED);
           mb.add(m_Examination);
        
              mi=new JMenuItem("Examination Details");
              m_Examination.add(mi);
              mi.addActionListener(this);
            
            m_UpdateDetails= new JMenu("Update Details");
           m_UpdateDetails.setForeground(Color.BLUE);
           mb.add(m_UpdateDetails);
           String arg6[] ={"Update Student Details","Update Teacher Details"};
           for(int i =0;i<arg6.length;i++)
            {
              mi=new JMenuItem(arg6[i]);
              m_UpdateDetails.add(mi);
              mi.addActionListener(this);
            }

            m_FeeDetails = new JMenu("Fee Details");
            m_FeeDetails.setForeground(Color.RED);
            mb.add(m_FeeDetails);
            String arg7[] ={"Fee Structure","Student fee form"};
            for(int i =0;i<arg7.length;i++)
            {
              mi=new JMenuItem(arg7[i]);
              m_FeeDetails.add(mi);
              mi.addActionListener(this);
            }

             m_Utility = new JMenu("Utility");
             m_Utility.setForeground(Color.BLUE);
             mb.add(m_Utility);
             String arg8[] ={"Notepad","Calculator","Web Browser"};

           for(int i =0;i<arg8.length;i++)
            {
              mi=new JMenuItem(arg8[i]);
              m_Utility.add(mi);
              mi.addActionListener(this);
            }

            m_About = new JMenu("About");
            m_About.setForeground(Color.RED);
            mb.add(m_About);
            mi=new JMenuItem("About us");
            m_About.add(mi);
            mi.addActionListener(this);

            m_Exit = new JMenu("Exit");
            m_Exit.setForeground(Color.BLUE);
            mb.add(m_Exit);
            mi=new JMenuItem("Exit");
            m_Exit.add(mi);
            mi.addActionListener(this);
            ImageIcon img = new ImageIcon("images/new1.jpg");
            Image i2=img.getImage().getScaledInstance(1500,750,Image.SCALE_DEFAULT);
            ImageIcon i3=new ImageIcon(i2);

            lbl = new JLabel(i3);        
            this.add(lbl);
       
            this.setVisible(true);
            this.setSize(1540,850);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
          String text=e.getActionCommand();

          switch(text)
          {
          case"Exit":
            this.dispose();
            break;

          case"Calculator":
            try
            {
              Runtime.getRuntime().exec("calc.exe");
            }
            catch(Exception ex) 
            {
                ex.printStackTrace();
            }
            break;

        case"Notepad":
            try
            {
              Runtime.getRuntime().exec("notepad.exe");
            }
            catch(Exception ex) 
            {
                ex.printStackTrace();
            }
            break;

        case"Web Browser":
             try
             {
                Desktop.getDesktop().browse(new URL("http://www.google.com").toURI());
             }
             catch(Exception ex)
             {
                  ex.printStackTrace();
             }

            break;
        
        case"New Student Information":

            AddStudent as=new AddStudent();          
            break;

        case"New Faculty Information":

            AddTeacher at=new AddTeacher();           
            break;

        case"View Student Details":
            StudentDetails sd = new StudentDetails();
            break;

        case"View Teacher Details":
            TeacherDetails td = new TeacherDetails();
            break;

        case"Update Student Details":
             UpdateStudent us= new UpdateStudent();
            break;

        case"Update Teacher Details":
              UpdateTeacher ut=new UpdateTeacher(); 
            break;

        

        case"Teacher Leave":
            TeacherLeave tl = new TeacherLeave();
            break;


        case"Student Leave":
            StudentLeave sl = new StudentLeave();
            break;
        case"Student Leave Details":

            StudentLeaveDetails sld=new StudentLeaveDetails();
            break;

        case"Teacher Leave Details":
            TeacherLeaveDetails tld=new TeacherLeaveDetails();
            break;

        case"Fee Structure":
            FeeStructure fs = new FeeStructure();
            break;

        case"Student fee form":
            StudentFeeForm sff = new StudentFeeForm();
            break;

        case"Examination Details":
            ExaminationDetails ed = new ExaminationDetails();
            break;

        case"About us":
            About a=new About();
            break;
          }
        }
    }

     
class RegisterFrame extends JFrame implements ActionListener
{
   private JTextField txt_first_name,txt_middle_name,txt_last_name,txt_password,txt_username;
   private JTextArea ta_address;
   private JButton b;
   private JLabel lbl;
   private JPanel p;
   private JScrollPane jsp;
	public RegisterFrame()
	{

        ImageIcon img=new ImageIcon("Images/register.jpg");
        Image i2=img.getImage().getScaledInstance(350,300,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel lbl=new JLabel(i3);
        lbl.setBounds(360,40,300,380);
        this.add(lbl);

         lbl = new JLabel("REGISTER FORM",JLabel.CENTER);
	     lbl.setFont(new Font("Calibri",Font.BOLD,30));
	     this.add(lbl,BorderLayout.NORTH);

         p=new JPanel();
         p.setLayout(null);
         p.setBackground(Color.PINK);
          lbl=new JLabel("FIRST NAME",JLabel.RIGHT);
          lbl.setBounds(20,40,135,35);
          p.add(lbl);

          txt_first_name=new JTextField();
          txt_first_name.setBounds(165,40,180,35);
          p.add(txt_first_name);

          lbl=new JLabel("MIDDLE NAME",JLabel.RIGHT);
          lbl.setBounds(20,85,135,35);
          p.add(lbl);

          txt_middle_name=new JTextField();
          txt_middle_name.setBounds(165,85,180,35);
          p.add(txt_middle_name);
         
          lbl=new JLabel("LAST NAME",JLabel.RIGHT);
          lbl.setBounds(20,130,135,35);
          p.add(lbl);

          txt_last_name=new JTextField();
          txt_last_name.setBounds(165,130,180,35);
          p.add(txt_last_name);

          lbl=new JLabel("ADDRESS",JLabel.RIGHT);
          lbl.setBounds(20,175,135,35);
          p.add(lbl);
          ta_address=new JTextArea();
          jsp = new JScrollPane(ta_address);
          jsp.setBounds(165,175,180,35);
          p.add(jsp);

          lbl=new JLabel("USERNAME",JLabel.RIGHT);
          lbl.setBounds(20,220,135,35);
          p.add(lbl);

          txt_username=new JTextField();
          txt_username.setBounds(165,220,180,35);
          p.add(txt_username);

          lbl=new JLabel("PASSWORD",JLabel.RIGHT);
          lbl.setBounds(20,265,135,35);
          p.add(lbl);

          txt_password=new JTextField();
          txt_password.setBounds(165,265,180,35);
          p.add(txt_password);

          b=new JButton("REGISTER");
          b.setBackground(Color.BLACK);
          b.setForeground(Color.WHITE);
          b.setBounds(20,310,135,35);
          p.add(b);
          b.addActionListener(this);

          b=new JButton("BACK TO LOGIN");
          b.setBackground(Color.BLACK);
          b.setForeground(Color.WHITE);
          b.setBounds(165,310,180,35);
          p.add(b);
          b.addActionListener(this);

         this.add(p,BorderLayout.CENTER);
		 this.setVisible(true);
	     this.setSize(700,450);
         this.setLocation(400,150);
	     this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
    @Override
    public void actionPerformed(ActionEvent e)
    {
           String text=e.getActionCommand();
           switch(text)
           {
              case"REGISTER":
                String fn = txt_first_name.getText().trim().toUpperCase();
                String mn= txt_middle_name.getText().trim().toUpperCase();
                String ln = txt_last_name.getText().trim().toUpperCase();
                String ad = ta_address.getText().trim().toUpperCase();
                String username = txt_username.getText();
                String password = txt_password.getText();

                if(fn.isEmpty()||mn.isEmpty()||ln.isEmpty()||ad.isEmpty()||username.isEmpty()||password.isEmpty())
                {
                   JOptionPane.showMessageDialog(this,"Fill up the form properly..!","ERROR",JOptionPane.ERROR_MESSAGE); 
                }
                else 
                {
                try 
                {
               DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb","root","super");
               String sql = " insert into login( first_name,middle_name,last_name,address,username,pass)values (?,?,?,?,?,?)";
               PreparedStatement ps = con.prepareStatement(sql);
              
                ps.setString(1,fn);
                ps.setString(2,mn);
                ps.setString(3,ln);
                ps.setString(4,ad);
                ps.setString(5,username);
                ps.setString(6,password);

                int n = ps.executeUpdate();
                con.close();
                ps.close();
                if(n==1)
                {
                 JOptionPane.showMessageDialog(this,"User data inserted..","SUCCESS",JOptionPane.INFORMATION_MESSAGE);

                }
                else 
                {
                JOptionPane.showMessageDialog(this,"User data not inserted..","ERROR",JOptionPane.ERROR_MESSAGE);
                }              
                txt_first_name.setText("");
                txt_middle_name.setText("");
                txt_last_name.setText("");
                ta_address.setText("");
                txt_username.setText("");
                txt_password.setText("");

                 }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(this,"PASSWORD EXIST","ERROR",JOptionPane.ERROR_MESSAGE);
                }

                }
                break;


             case"BACK TO LOGIN":
                 
                 new LoginFrame();
                 this.dispose();
                 break;
           }
    }
}

class LoginFrame extends JFrame implements ActionListener
{ 
	private JLabel lbl;
	private JTextField txt_username;
	private JButton b;
	private JPanel p1,p2;
    private Connection con =null;
    private PreparedStatement ps =null;
    private JCheckBox jbx;
    private JPasswordField txt_password;   
     public LoginFrame()
	{   
		

	     lbl = new JLabel("LOGIN FORM",JLabel.CENTER);
	     lbl.setFont(new Font("Calibri",Font.BOLD,30));
	     this.add(lbl,BorderLayout.NORTH);

         p1 = new JPanel();
         p1.setLayout(null);
         p1.setBackground(Color.PINK);

         
         lbl = new JLabel("USERNAME",JLabel.CENTER);
         lbl.setBounds(220,70,135,35);
         p1.add(lbl);
         txt_username = new JTextField();
         txt_username.setBounds(365,70,170,35);
         p1.add(txt_username);
         lbl = new JLabel("PASSWORD",JLabel.CENTER);
         lbl.setBounds(220,120,135,35);
         p1.add(lbl);
         txt_password = new JPasswordField();
         txt_password.setBounds(365,120,170,35);
         p1.add(txt_password); 

         jbx = new JCheckBox("Show Password");
         jbx.addActionListener(this);
         jbx.setBounds(365,175,170,35);
         p1.add(jbx);    
        
         b = new JButton("REGISTER");
         b.setBackground(Color.BLACK);
         b.setForeground(Color.WHITE);
         	b.setBounds(220,220,135,35);
         	p1.add(b);
         	b.addActionListener(this);

         	b = new JButton("LOGIN");
            b.setBackground(Color.BLACK);
            b.setForeground(Color.WHITE);
         	b.setBounds(365,220,170,35);
         	p1.add(b);
         	b.addActionListener(this);

        ImageIcon img=new ImageIcon("Images/login.jpg");
        Image i2=img.getImage().getScaledInstance(350,300,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel lbl=new JLabel(i3);
        lbl.setBounds(10,50,180,240);
        this.add(lbl);

         this.add(p1,BorderLayout.CENTER);
	     this.setVisible(true);
	     this.setSize(600,350);
         this.setLocation(450,200);
	     this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      }
   @Override
   public void actionPerformed(ActionEvent e)
   {
   
        String text=e.getActionCommand();
        switch(text)
        {

             case"LOGIN":
                
              String username = txt_username.getText();
              String password = txt_password.getText();
              if(username.isEmpty()||password.isEmpty())
              {
                JOptionPane.showMessageDialog(this,"Username and/or Password is required..","ERROR",JOptionPane.ERROR_MESSAGE);
              }
              else 
              {
                //Start the login process
              try 
              {
               DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb","root","super");
               String sql = "select * from login where username=? and pass=?";
                ps = con.prepareStatement(sql);
                ps.setString(1,username);
                ps.setString(2,password);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                {
                    //display database or new page after login
                    new MainFrame();
                    this.dispose();
                }
                else 
                {
                
                   JOptionPane.showMessageDialog(this,"Username and Password is not found..","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                con.close();
                rs.close();
                ps.close();
                
                 }
                 catch(Exception ex)
                  {
                    JOptionPane.showMessageDialog(this,ex,"EXCEPTION",JOptionPane.ERROR_MESSAGE);
                  }
             }
                break;

             case"REGISTER":

                new RegisterFrame();
                this.dispose();
                break;

            case"Show Password":
                if(jbx.isSelected())
                {
                  txt_password.setEchoChar((char)0);
                }
                else 
                {
                    txt_password.setEchoChar('*');
                }
                break;
    }        
   }
}
class MyFrame extends JFrame implements Runnable 
{
    private JLabel lbl1;
    private JLabel lbl2;
    private JProgressBar jpb;
    private JPanel p;
    private Thread t;

    public MyFrame()
    {

        ImageIcon img = new ImageIcon("images/me.jpg"); 
    	lbl1 = new JLabel(img);
    	
    	this.add(lbl1,BorderLayout.CENTER);
        p= new JPanel();
        p.setLayout(new GridLayout(2,1));
    	lbl2 = new JLabel("0%",JLabel.CENTER);
    	lbl2.setFont(new Font("ALGERIAN",Font.BOLD,15));
    	p.add(lbl2);

    	jpb = new JProgressBar(SwingConstants.HORIZONTAL,0,100);
    	jpb.setForeground(Color.BLACK);
        jpb.setBackground(Color.WHITE);

    	p.add(jpb);
    	this.add(p,BorderLayout.SOUTH);
	   
	    t = new Thread(this);
	    t.start();
        this.setVisible(true);
	    this.setSize(800,500);
        this.setLocation(350,150);
	    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
}
 public void run()
 { 
    try
    {
 	   for(int i=0;i<=100;i++)
 	   {
 	      jpb.setValue(i);
 	
 	      long v = Math.round(jpb.getPercentComplete()*100);
 	      lbl2.setText(v+"%");
          Thread.sleep(100);
        }
    }
    catch(Exception ex)
    {
	    ex.printStackTrace();
    }
       new LoginFrame();
       this.dispose();
  }
}
class  InstituteManagement
{
	public static void main(String[] args)
     {
		new MyFrame(); 
	}
}