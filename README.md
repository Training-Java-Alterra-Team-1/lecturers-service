# Microservice Dosen

Anggota Tim Developer:
* Jalaluddin Al Mursyidy Fadhlurrahman
* Djodi Ramadhan

Proyek ini merupakan bentuk sistem akademik bagi lingkungan Perguruan Tinggi yang terdiri dari 3 _microservices_ :
1. Service Auth
2. Service Registrasi Mahasiswa Baru
3. Service KRS

Berikut merupakan _Entity Relationship Diagram_ untuk proyek ini:
![alt text](https://github.com/Training-Java-Alterra-Team-1/sistem-akademik-auth/blob/master/ERD%20-%20Sistem%20Informasi%20Akademik.png "ER Diagram")

<details>
	<summary>Penjelasan ERD</summary>
	<ol> 
		<li>Entitas</li>
		<ol>
			<li>Dosen (Lecturers)</li>
			<li>Mahasiswa (Students)</li>
			<li>Jenjang (Degree)</li>
			<li>Jurusan (Major)</li>
			<li>Fakultas (Department)</li>
			<li>Mata Kuliah (Courses)</li>
			<li>Transkrip (Student records)</li>
			<li>User</li>
			<li>Role</li>
		</ol>
		<li>Hubungan antar entitas</li>
		<ol>
			<li>1 Dosen dapat mengampu banyak mata kuliah</li>
			<li>1 Mahasiswa dapat mengambil banyak mata kuliah</li>
			<li>1 Jurusan dapat memiliki banyak dosen</li>
			<li>1 Jurusan dapat memiliki banyak mahasiswa</li>
			<li>1 Jurusan dapat memiliki banyak jenjang/degree (S1, S2, dan S3)</li>
			<li>1 Departemen/Fakultas dapat memiliki banyak jurusan</li>
			<li>Tiap-tiap mahasiswa/dosen memiliki 1 user</li>
			<li>Tiap-tiap user dapat memiliki banyak roles</li>
		</ol>
	</ol>
</details>