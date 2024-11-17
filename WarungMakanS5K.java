import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Kelas dasar Produk untuk merepresentasikan atribut umum dari produk
class Produk {
    private String nama;
    private double harga;

    public Produk(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }
}

// Kelas Menu yang mewarisi dari Produk dan menambahkan atribut kategori
class Menu extends Produk {
    private String kategori;

    public Menu(String nama, double harga, String kategori) {
        super(nama, harga); // Memanggil konstruktor dari kelas Produk
        this.kategori = kategori;
    }

    public String getKategori() {
        return kategori;
    }
}

// Kelas Inventaris untuk menyimpan data menu makanan
class Inventaris {
    private List<Menu> daftarMenu;

    public Inventaris() {
        daftarMenu = new ArrayList<>();
        // Menambahkan data menu
        daftarMenu.add(new Menu("Nasi Goreng", 15000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Goreng", 25000, "Makanan"));
        daftarMenu.add(new Menu("Sate", 30000, "Makanan"));
        daftarMenu.add(new Menu("Mie Ayam", 20000, "Makanan"));
        daftarMenu.add(new Menu("Bakso", 15000, "Makanan"));
    }

    public List<Menu> getDaftarMenu() {
        return daftarMenu;
    }

    public void tampilkanDaftarMenu() {
        System.out.println("Daftar Menu Tersedia:");
        for (Menu menu : daftarMenu) {
            System.out.println(menu.getNama() + " - Rp" + menu.getHarga() + " (" + menu.getKategori() + ")");
        }
    }
}

// Kelas Keranjang untuk mengelola item menu
class Keranjang {
    private List<Menu> menuList;
    private List<Integer> jumlahList; // Menyimpan jumlah pesanan untuk setiap menu

    public Keranjang() {
        menuList = new ArrayList<>();
        jumlahList = new ArrayList<>();
    }

    public void tambahMenu(Menu menu, int jumlah) {
        menuList.add(menu);
        jumlahList.add(jumlah);
    }

    public double hitungTotal() {
        double total = 0;
        for (int i = 0; i < menuList.size(); i++) {
            total += menuList.get(i).getHarga() * jumlahList.get(i);
        }
        return total;
    }

    public int hitungTotalItem() {
        int totalItem = 0;
        for (int jumlah : jumlahList) {
            totalItem += jumlah;
        }
        return totalItem;
    }

    public void cetakStruk(double pembayaran, double kembalian, double diskon) {
        double total = hitungTotal();
        double totalSetelahDiskon = total - (total * (diskon / 100)); // Hitung total setelah diskon

        System.out.println("\n                  WARUNG MAKAN S5K                     ");
        System.out.println("\n          JL. SUKA MAPIR KEL. SUKA BELI            ");
        System.out.println("\n     Kritik & Saran: 112233 S5Kmart@emu.co.id      ");
        System.out.println("\n               WA/SMS : 0812122112211              ");
        System.out.println("\n================== STRUK BELANJA ===================");
        System.out.println("No   Nama Menu           Jumlah     Harga");
        System.out.println("====================================================");
        int nomor = 1;
        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = menuList.get(i);
            int jumlah = jumlahList.get(i);
            System.out.printf("%-4d %-20s %-10d Rp%.2f\n", nomor++, menu.getNama(), jumlah, menu.getHarga() * jumlah);
        }
        System.out.println("====================================================");
        System.out.printf("Total Item:               %d\n", hitungTotalItem());
        System.out.printf("Total:                               Rp%.2f\n", total);
        System.out.printf("Diskon:                              %.2f%%\n", diskon);
        System.out.printf("Total Setelah Diskon:                Rp%.2f\n", totalSetelahDiskon);
        System.out.printf("Pembayaran:                          Rp%.2f\n", pembayaran);
        System.out.printf("Kembalian:                           Rp%.2f\n", kembalian);
        System.out.println("====================================================");
        System.out.println("          Terimakasih Telah Berbelanja");
        System.out.println("            Silahkan Datang Kembali");
        System.out.println("====================================================");
    }
}

// Kelas Kasir untuk mengelola interaksi dengan pengguna
class Kasir {
    private Keranjang keranjang;
    private Inventaris inventaris;
    private Scanner scanner;

    public Kasir() {
        keranjang = new Keranjang();
        inventaris = new Inventaris();
        scanner = new Scanner(System.in);
    }

    public void mulai() {
        inventaris.tampilkanDaftarMenu();
        String input;
        do {
            System.out.print("\nMasukkan nama menu yang ingin dibeli (atau ketik 'selesai' untuk keluar): ");
            input = scanner.nextLine();
            Menu menuTerpilih = cariMenu(input);
            if (menuTerpilih != null) {
                System.out.print("Masukkan jumlah yang ingin dibeli: ");
                int jumlah = scanner.nextInt();
                scanner.nextLine(); // membersihkan newline
                keranjang.tambahMenu(menuTerpilih, jumlah);
                System.out.println(jumlah + " " + menuTerpilih.getNama() + " telah ditambahkan ke keranjang.");
            } else if (!input.equalsIgnoreCase("selesai")) {
                System.out.println("Menu tidak ditemukan. Silakan coba lagi.");
            }
        } while (!input.equalsIgnoreCase("selesai"));

        hitungKembalian(); // Meminta pembayaran sebelum mencetak struk
    }

    private Menu cariMenu(String nama) {
        for (Menu menu : inventaris.getDaftarMenu()) {
            if (menu.getNama().equalsIgnoreCase(nama)) {
                return menu;
            }
        }
        return null; // Jika menu tidak ditemukan
    }

    private void hitungKembalian() {
        double total = keranjang.hitungTotal();
        System.out.printf("Total Belanja: Rp%.2f\n", total);
        System.out.print("Masukkan nominal pembayaran: Rp");
        double pembayaran = scanner.nextDouble();
        scanner.nextLine(); // membersihkan newline

        // Meminta pengguna memasukkan diskon dalam persen
        System.out.print("Masukkan jumlah diskon (dalam persen): ");
        double diskon = scanner.nextDouble();
        scanner.nextLine(); // membersihkan newline

        if (pembayaran >= total - (total * (diskon / 100))) {
            double kembalian = pembayaran - (total - (total * (diskon / 100)));
            keranjang.cetakStruk(pembayaran, kembalian, diskon); // Mencetak struk setelah mendapatkan pembayaran
        } else {
            System.out.println("Nominal pembayaran tidak cukup. Silakan coba lagi.");
            hitungKembalian(); // Ulangi jika pembayaran tidak cukup
        }
    }
}

// Kelas utama untuk menjalankan program
public class WarungMakanS5K {
    public static void main(String[] args) {
        Kasir kasir = new Kasir();
        kasir.mulai();
    }
}
