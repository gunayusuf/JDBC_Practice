import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class P5_CRUD {
    // Cok miktarda kayit eklemek icin PreparedStatement metodu kullanilabilir.
    // PreparedStatement hem hizli hem de daha guvenli (SQL injection saldirilari icin) bir yontemdir.
    // ResultSet tum sonucları bellekte tutuyor..  ama guvenli degil..banka hesaplarını dusunun..siber saldırıya ugrasa..
    // bu yuzden database in guvrnligi acisindan PreparedStatement onemlidir..
    // Bunun icin;
    // 	1) Veri girisine uygun bir POJO(Plain Old Java Object) sinifi olusturulur.
    // 	2) POJO Class nesnelerini saklayacak bir collection olusturulur
    // 	3) bir dongu ile kayitlar eklenir.

    // PreparedStatement hizli ve guvenli yontem, bu yontemle cozecegiz
    // POJO class : plain old java object class, bu class calistirilmaz main i yok..
    // bu class private degisken + constructor() + getter() + setter() ve toString() ler iceren class


    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/batch60_61?serverTimezone=UTC";
        Connection con = DriverManager.getConnection(url, "root", "1234");  //database baglantisi icin

        Statement st = con.createStatement();  //query calistirmak icin

        // SORU: Dersler adında bir tablo olusturalım (id int, ders_isim varchar(15), egitim_suresi int)
        String createQuery = "create table dersler (id int, ders_isim VARCHAR(15), egitim_suresi_gun int)";

        st.execute(createQuery);
        System.out.println("tablo olusturuldu..");

        //Not : Ders diye bir pojo class olustururuz,
        // id(int), ders_isim(String), egitim_suresi(int) instance variable ları olusturup,
        // Generate den constructor , getter setter ve toString() methodlari cagiririz

        List<Ders> dersler = new ArrayList<>();
        dersler.add(new Ders (100, "Java",50));
        dersler.add(new Ders (101, "SQL",10));
        dersler.add(new Ders (102, "JDBC",2));
        dersler.add(new Ders (103, "APi",12));
        dersler.add(new Ders (104, "JENKİNS",1));
        dersler.add(new Ders (105, "Selenium",25));
        dersler.add(new Ders (106, "Appium",10));
        dersler.add(new Ders (107, "SDLC",10));

        PreparedStatement table = con.prepareStatement("INSERT INTO dersler VALUES (?,?,?)");  //hangi formatta ise tablo o verileri dusunerek value ları yazariz

        for (Ders d : dersler){
            table.setInt(1, d.getId());
            table.setString(2, d.getDers_isim());
            table.setInt(3, d.getEgitim_suresi());

            table.addBatch();
        }

        table.executeBatch();


        System.out.println("Hersey Tamam");
        //mySql de derslerin hizasindaki son simgeye basilirsa tablo SQL de gorunur..

    }

}
