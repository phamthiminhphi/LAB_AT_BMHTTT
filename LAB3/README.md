# BÁO CÁO THỰC HÀNH LAB 3: NHẬN DIỆN VÀ ỨNG PHÓ CÁC MỐI ĐE DỌA ĐẾN AN TOÀN THÔNG TIN

---

## 1. THÔNG TIN SINH VIÊN & BÀI THỰC HÀNH
* **Họ và tên:** [Điền Họ và Tên của bạn]
* **Mã số sinh viên (MSSV):** [Điền MSSV của bạn]
* **Tên bài Lab:** Lab 3 – Nhận diện và ứng phó các mối đe dọa đến an toàn thông tin
* **Môn học:** An toàn Hệ thống Thông tin
* **Thời gian thực hiện:** Tháng 09/2026 (Khớp timestamp hệ thống: 22/09/2026)

---

## 2. PHIÊN BẢN MÔI TRƯỜNG THỰC HÀNH
* **Hệ điều hành máy ảo (Guest OS):** Windows Server (chạy trên VMware Workstation)
* **Quyền thực thi:** Administrator / PowerShell v5.1+
* **Các công cụ & Công nghệ sử dụng:**
  * **Microsoft Defender Antivirus:** Engine và Real-time Protection kích hoạt
  * **Sysmon (System Monitor):** v15.22
  * **Sysinternals Autoruns:** v14.3 (`autorunsc64.exe`)
  * **Sysinternals Process Explorer:** v17.14
  * **Wireshark:** v4.6.8 (kèm Npcap Loopback Adapter)
  * **Python:** v3.x (dùng cho HTTP loopback server & script kiểm thử tải)
  * **Giao thức mạng:** HTTP/1.1 (Loopback port 8080), TLSv1.3 (HTTPS port 443)

---

## 3. CÁCH DỰNG & THIẾT LẬP MÔI TRƯỜNG
1. **Cấu hình mạng:**
   * Mặc định đặt card mạng máy ảo ở chế độ **Host-only** để đảm bảo tính cô lập, không phát sinh lưu lượng ra mạng ngoài.
   * Chuyển tạm thời sang chế độ **NAT** chỉ trong vài phút ở bước kiểm thử HTTPS tới `example.com` (TH5), sau đó chuyển ngay lại về **Host-only**.
2. **Cấu trúc thư mục làm việc:**
   * Thư mục chứa công cụ: `C:\LAB3\Tools\` (Sysmon, Autoruns, Process Explorer, Wireshark).
   * Thư mục tài nguyên huấn luyện: `C:\LAB3\lab3_assets\` (chứa `scripts`, `data`, `samples`, `www`).
   * Thư mục xuất bằng chứng: `C:\LAB3\Evidence\`.
3. **Kích hoạt dịch vụ:**
   * Bật giám sát Sysmon với file cấu hình chuẩn.
   * Khởi chạy Web Server cục bộ phục vụ giả lập:
     ```powershell
     python -m http.server 8080 --bind 127.0.0.1
     ```

---

## 4. BẢNG TỔNG HỢP CÁC TÌNH HUỐNG & KẾT QUẢ ĐẠT ĐƯỢC

| Tình huống | Nội dung thực hiện | Bằng chứng tạo ra | Kết quả |
| :--- | :--- | :--- | :---: |
| **TH1 - TH3** | Quản trị tài khoản `lab3user`, xoay vòng mật khẩu, kiểm thử chuỗi EICAR với Defender, kiểm tra Event Viewer (4624, 4625, 4648). | `auth_events_before_rotation.txt`, `baseline_*.txt`, `defender_eicar.txt` | **PASS** |
| **TH4** | Thiết lập persistence lành tính (`LAB3_Run_Demo`, Task), chạy Web Server cục bộ trên cổng 8080, kiểm tra Sysmon Event 1 và Process Explorer. | `H6_Sysmon_Event1.png`, `H7_Autoruns_LAB3_Run_Demo.png`, `H8_ProcessExplorer_Python.png` | **PASS** |
| **TH5** | So sánh lưu lượng mạng HTTP (plaintext) qua port 8080 và HTTPS (mã hóa) qua TLS 1.3 port 443 bằng Wireshark. | `H9_HTTP_Plaintext.png`, `H10_TLS_443.png` | **PASS** |
| **TH6** | Kiểm thử tải cục bộ 50 request tới `127.0.0.1:8080`, phân tích dataset DDoS (dải TEST-NET) và log Mail Bombing offline. | `local_load_test.txt`, `ddos_sources.txt`, `mail_sender_counts.txt`, `mail_volume.txt`, `H10_Load_and_Log_Analysis.png` | **PASS** |
| **TH7** | Phân tích 5 chỉ dấu lừa đảo trong `phishing_email.txt`, phân loại 6 kịch bản Social Engineering trong `social_engineering_cases.csv`. | `H10_Phishing_Offline.png` | **PASS** |
| **Mục 8 (Cleanup)** | Dọn dẹp persistence, tắt server port 8080, xóa tài khoản lab, đối chiếu Autoruns diff, băm SHA-256 toàn bộ thư mục Evidence. | `autoruns_diff.txt`, `H11_Recovery_Verification.png`, `evidence_sha256.csv` | **PASS** |

---

## 5. CÁC LỖI GẶP PHẢI TRONG QUÁ TRÌNH THỰC HIỆN & CÁCH KHẮC PHỤC

### 1. Lỗi phân tích cú pháp Pipeline trong PowerShell (`Unexpected token '\vert'`)
* **Hiện tượng:** Khi dán lệnh phân tích log DDoS và Mail Bombing, PowerShell báo lỗi đỏ: `Unexpected token '\vert' in expression or statement`.
* **Nguyên nhân:** Ký tự pipe `|` bị mã hóa nhầm thành cú pháp LaTeX `\vert{}` trong quá trình sao chép lệnh.
* **Cách khắc phục:** Chỉnh sửa lại toàn bộ chuỗi lệnh về đúng ký tự pipe gốc `|` của PowerShell trước khi chạy:
  ```powershell
  $log | Group-Object Sender | Sort-Object Count -Descending | Select-Object Count, Name | Tee-Object C:\LAB3\Evidence\mail_sender_counts.txt
  ```

### 2. Không thấy tham số Plaintext khi bắt gói tin HTTPS (TH5)
* **Hiện tượng:** Khi bắt gói tin gửi tới `https://example.com/`, dùng filter tìm chuỗi `TRAINING_ONLY` nhưng không thấy nội dung URL hay tham số.
* **Nguyên nhân:** Phiên kết nối sử dụng giao thức **TLSv1.3**, toàn bộ phần dữ liệu HTTP Request đã bị mã hóa đối xứng thành trường `Application Data`.
* **Cách khắc phục:** Hiểu đúng bản chất của mã hóa đường truyền: việc không thấy plaintext chính là minh chứng cho cơ chế bảo vệ của TLS. Mở rộng trường `Transport Layer Security` $\rightarrow$ `Encrypted Application Data` để chụp ảnh chứng minh tính bảo mật so với HTTP.

### 3. Lỗi xung đột tệp khi chạy lệnh tính băm SHA-256 (`File being used by another process`)
* **Hiện tượng:** Khi chạy `Get-ChildItem C:\LAB3\Evidence -File | Get-FileHash | Export-Csv evidence_sha256.csv`, xuất hiện lỗi không thể đọc tệp `evidence_sha256.csv`.
* **Nguyên nhân:** Lệnh `Export-Csv` đang mở tệp để ghi trong khi lệnh `Get-ChildItem` lại quét trúng chính tệp này và đưa vào hàm băm cùng lúc.
* **Cách khắc phục:** Lọc loại trừ file kết quả ra khỏi luồng xử lý và đưa toàn bộ danh sách file vào bộ nhớ bằng toán tử `@(...)`:
  ```powershell
  Remove-Item C:\LAB3\Evidence\evidence_sha256.csv -Force -ErrorAction SilentlyContinue
  @(Get-ChildItem C:\LAB3\Evidence -File | Where-Object { $_.Name -ne 'evidence_sha256.csv' }) |
      Get-FileHash -Algorithm SHA256 |
      Export-Csv C:\LAB3\Evidence\evidence_sha256.csv -NoTypeInformation -Encoding UTF8
  ```

### 4. Tệp `sysmon_persistence.txt` bị rỗng (0 KB)
* **Hiện tượng:** Kiểm tra trong File Explorer thấy tệp log persistence có kích thước 0 KB.
* **Nguyên nhân:** Lệnh trích xuất Sysmon trước đó bị ngắt hoặc bộ lọc chưa bắt trúng event.
* **Cách khắc phục:** Chạy lại truy vấn Event Log của Sysmon với các Event ID quan trọng (1, 11, 12, 13, 14) và ghi đè nội dung mới:
  ```powershell
  Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational'; Id=1,11,12,13,14} -MaxEvents 50 |
      Select-Object TimeCreated, Id, Message |
      Out-File C:\LAB3\Evidence\sysmon_persistence.txt -Width 300 -Encoding utf8
  ```

### 5. Snapshot Manager chỉ hiển thị `You Are Here`
* **Hiện tượng:** Khi chuẩn bị revert snapshot ở Bước 8, mở Snapshot Manager trên VMware chỉ thấy `You Are Here` mà không có snapshot cũ.
* **Nguyên nhân:** Máy ảo bàn giao chưa được tạo sẵn snapshot mốc ban đầu.
* **Cách khắc phục:** Vì lệnh dọn dẹp ở Bước 8 (xóa Registry Run, gỡ Scheduled Task, dừng process port 8080, xóa user `lab3user`) đã làm sạch hoàn toàn hệ thống, sinh viên đóng cửa sổ Snapshot Manager và tắt máy ảo bình thường.

---

## 6. TUÂN THỦ NGUYÊN TẮC AN TOÀN VÀ ĐẠO ĐỨC
* **Tính xác thực:** Toàn bộ ảnh chụp màn hình (`H1` đến `H11`) được chụp trực tiếp từ máy ảo sinh viên, khớp chính xác thời gian hệ thống và tài khoản thực thi.
* **Bảo vệ dữ liệu:** Không lưu trữ mật khẩu thật, API key, token hay thông tin định danh cá nhân/hệ thống thực tế vào thư mục bài nộp.
* **Phạm vi kiểm thử:** Script `local_load_test.py` được cấu hình cố định trỏ về `127.0.0.1:8080`; toàn bộ phân tích DDoS và Mail Bombing đều thực hiện ngoại tuyến trên dataset mẫu (dải IP TEST-NET và domain `.example/.invalid`), tuyệt đối không gửi tải hay phát tán thư rác ra môi trường mạng ngoài.