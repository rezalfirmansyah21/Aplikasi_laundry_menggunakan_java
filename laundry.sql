-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Jun 2020 pada 11.20
-- Versi server: 10.1.37-MariaDB
-- Versi PHP: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `laundry`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `admin`
--

CREATE TABLE `admin` (
  `id` int(128) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nama_admin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `nama_admin`) VALUES
(2, 'sukma', '121212', 'Sukma Dewi'),
(3, 'aswad', '121212', 'Aswad Rifani');

-- --------------------------------------------------------

--
-- Struktur dari tabel `paket`
--

CREATE TABLE `paket` (
  `kode` varchar(128) NOT NULL,
  `nama_paket` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `paket`
--

INSERT INTO `paket` (`kode`, `nama_paket`, `harga`, `tanggal`) VALUES
('L0001', 'Cuci gosok normal', 6000, '2020-06-20'),
('L0002', 'Cuci gosok kilat', 8000, '2020-06-20'),
('L0003', 'Gosok saja normal', 3000, '2020-06-20'),
('L0004', 'Gosok saja kilat', 5000, '2020-06-20'),
('L0005', 'Cuci saja normal', 3000, '2020-06-20'),
('L0006', 'Cuci saja kilat', 5000, '2020-06-24');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id` varchar(128) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `no_telp` varchar(12) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pelanggan`
--

INSERT INTO `pelanggan` (`id`, `nama`, `no_telp`, `alamat`, `tanggal`) VALUES
('C0001', 'Adi', '081290129012', 'Jakarta', '2020-06-20'),
('C0002', 'Rizka', '081290389012', 'Jakarta', '2020-06-24'),
('C0003', 'Deni', '089609879012', 'Jakarta', '2020-06-25');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penerimaan`
--

CREATE TABLE `penerimaan` (
  `id_penerimaan` int(11) NOT NULL,
  `kode` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `paket` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlah` int(50) NOT NULL,
  `total` int(50) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penerimaan`
--

INSERT INTO `penerimaan` (`id_penerimaan`, `kode`, `id`, `nama`, `paket`, `harga`, `jumlah`, `total`, `tanggal`) VALUES
(9, 'P0001', 'C0001', 'Adi', 'Cuci gosok kilat', 8000, 2, 16000, '2020-06-21'),
(14, 'P0002', 'C0002', 'Rizka', 'Cuci saja normal', 3000, 2, 6000, '2020-06-24');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pengambilan`
--

CREATE TABLE `pengambilan` (
  `id_pengambilan` int(128) NOT NULL,
  `kode` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `paket` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlah` int(50) NOT NULL,
  `hargasemua` int(50) NOT NULL,
  `tanggal` date NOT NULL,
  `total` int(50) NOT NULL,
  `bayar` int(50) NOT NULL,
  `kembalian` int(50) NOT NULL,
  `jam` varchar(50) NOT NULL,
  `tanggalpengambilan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pengambilan`
--

INSERT INTO `pengambilan` (`id_pengambilan`, `kode`, `id`, `nama`, `paket`, `harga`, `jumlah`, `hargasemua`, `tanggal`, `total`, `bayar`, `kembalian`, `jam`, `tanggalpengambilan`) VALUES
(4, 'T0001', 'C0002', 'Rizka', 'Cuci saja normal', 3000, 2, 6000, '2020-06-24', 30000, 30000, 0, '22:04:26', '2020-06-24'),
(5, 'T0001', 'C0002', 'Rizka', 'Cuci gosok normal', 6000, 4, 24000, '2020-06-24', 30000, 30000, 0, '22:04:26', '2020-06-24'),
(6, 'T0002', 'C0002', 'Rizka', 'Cuci saja normal', 3000, 2, 6000, '2020-06-24', 6000, 10000, 4000, '22:13:24', '2020-06-24'),
(7, 'T0003', 'C0003', 'Deni', 'Cuci gosok kilat', 8000, 4, 32000, '2020-06-25', 32000, 40000, 8000, '13:42:00', '2020-06-25'),
(8, 'T0004', 'C0003', 'Deni', 'Cuci gosok normal', 6000, 2, 12000, '2020-06-25', 21000, 30000, 9000, '13:55:33', '2020-06-25'),
(9, 'T0004', 'C0003', 'Deni', 'Cuci saja normal', 3000, 3, 9000, '2020-06-25', 21000, 30000, 9000, '13:55:33', '2020-06-25'),
(10, 'T0005', 'C0003', 'Deni', 'Cuci gosok normal', 6000, 2, 12000, '2020-06-25', 18000, 20000, 2000, '14:01:25', '2020-06-25'),
(11, 'T0005', 'C0003', 'Deni', 'Cuci saja normal', 3000, 2, 6000, '2020-06-25', 18000, 20000, 2000, '14:01:25', '2020-06-25');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tmp_penerimaan`
--

CREATE TABLE `tmp_penerimaan` (
  `id_tmp` int(128) NOT NULL,
  `kode` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `paket` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlah` int(50) NOT NULL,
  `total` int(50) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tmp_pengambilan`
--

CREATE TABLE `tmp_pengambilan` (
  `id_tmp` int(128) NOT NULL,
  `kode` varchar(50) NOT NULL,
  `id` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `paket` varchar(50) NOT NULL,
  `harga` int(50) NOT NULL,
  `jumlah` int(50) NOT NULL,
  `hargasemua` int(50) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `paket`
--
ALTER TABLE `paket`
  ADD PRIMARY KEY (`kode`);

--
-- Indeks untuk tabel `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `penerimaan`
--
ALTER TABLE `penerimaan`
  ADD PRIMARY KEY (`id_penerimaan`);

--
-- Indeks untuk tabel `pengambilan`
--
ALTER TABLE `pengambilan`
  ADD PRIMARY KEY (`id_pengambilan`);

--
-- Indeks untuk tabel `tmp_penerimaan`
--
ALTER TABLE `tmp_penerimaan`
  ADD PRIMARY KEY (`id_tmp`);

--
-- Indeks untuk tabel `tmp_pengambilan`
--
ALTER TABLE `tmp_pengambilan`
  ADD PRIMARY KEY (`id_tmp`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT untuk tabel `penerimaan`
--
ALTER TABLE `penerimaan`
  MODIFY `id_penerimaan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `pengambilan`
--
ALTER TABLE `pengambilan`
  MODIFY `id_pengambilan` int(128) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT untuk tabel `tmp_penerimaan`
--
ALTER TABLE `tmp_penerimaan`
  MODIFY `id_tmp` int(128) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `tmp_pengambilan`
--
ALTER TABLE `tmp_pengambilan`
  MODIFY `id_tmp` int(128) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
