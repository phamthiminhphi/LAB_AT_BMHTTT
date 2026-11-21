Họ và tên: Phạm Thị Minh Phi 

Mã số sinh viên: 1150070033 

Lớp: 11\_DHTMDT 

Tên bài thực hành: LAB 2 Mã hóa đối xứng hiện đại



Phiên bản môi trường thực hành được triển khai toàn diện trên máy trạm chạy hệ điều hành Windows 11 đóng vai trò đồng thời làm máy phát triển ứng dụng khách và máy chủ lưu trữ dữ liệu cục bộ. Phần mềm phát triển mã nguồn sử dụng Visual Studio Code kết hợp bộ công cụ quản lý dự án Apache Maven với trình biên dịch Java Development Kit phiên bản 24 cùng thư viện mật mã BouncyCastle phiên bản 1.78.1 và trình điều khiển Microsoft JDBC phiên bản 12.8.1. Môi trường lập trình phụ trợ sử dụng Python phiên bản 3.13.2 tích hợp thư viện cryptography phiên bản 50.0.2 và giao diện đồ họa Tkinter. Hệ quản trị cơ sở dữ liệu là Microsoft SQL Server 2019 phiên bản 15.0.2210.4 kết hợp công cụ quản trị SQL Server Management Studio.



Cách dựng môi trường bắt đầu bằng việc cấu hình giao thức mạng TCP/IP trên SQL Server hoạt động ổn định tại địa chỉ nội bộ 127.0.0.1 qua cổng cố định 1433 và kích hoạt chế độ xác thực hỗn hợp. Trên cơ sở dữ liệu, kịch bản khởi tạo được thực thi để tạo mới cơ sở dữ liệu LAB2\_MaHoaHienDai bao gồm hai bảng lưu trữ là dbo.app\_users quản lý thông tin tài khoản người dùng và dbo.crypto\_history ghi nhận nhật ký thao tác, đồng thời thiết lập tài khoản phân quyền riêng biệt mang tên lab2\_user với quyền đọc và ghi dữ liệu để đảm bảo không lưu trữ thông tin nhạy cảm trực tiếp trong mã nguồn. Về phía ứng dụng, dự án Maven LAB2\_MaHoaHienDai được tổ chức độc lập trong gói week\_02 kèm thư mục chuyên biệt LAB2\_Python chứa mã nguồn kịch bản Python.



Tình huống một thực hiện kiểm thử thuật toán mã khối DES ở chế độ CBC với khóa chuẩn dài 8 byte là 12345678 trên chuỗi dữ liệu tiếng Việt có dấu Xin chào Lab 2 - Mã hóa hiện đại!. Ứng dụng sinh vectơ khởi tạo IV ngẫu nhiên 8 byte cho từng phiên làm việc giúp tạo ra bản mã Base64 khác nhau qua mỗi lần thực thi, khôi phục nguyên vẹn văn bản gốc khi giải mã đúng khóa và chặn đứng thành công các trường hợp nhập sai độ dài khóa hoặc sai khóa giải mã. Kết quả đạt PASS.



Tình huống hai kiểm chứng thuật toán TripleDES sử dụng khóa 24 byte gồm chuỗi 24 ký tự không dấu 123456789012345678901234 để thực hiện quy trình mã hóa và giải mã ba vòng liên tiếp. Chương trình đảm bảo tính toàn vẹn dữ liệu tiếng Việt khi khôi phục từ chuỗi Base64, đồng thời cơ chế kiểm tra đầu vào phát hiện và từ chối chính xác khi khóa bị thiếu xuống còn 23 byte. Kết quả đạt PASS.



Tình huống ba triển khai hệ thống đăng ký, đăng nhập tài khoản và mã hóa xác thực bằng thuật toán AES 256 bit ở chế độ GCM kết hợp kết nối SQL Server. Hệ thống băm mật khẩu người dùng sinhvien01 bằng thuật toán PBKDF2 với 120000 vòng lặp kèm muối ngẫu nhiên 16 byte trước khi lưu vào bảng dbo.app\_users, sau đó cấp vé phiên làm việc Ticket dài 32 ký tự thập lục phân khi đăng nhập thành công. Mọi thao tác mã hóa, giải mã bằng khóa MatKhauAES@2026 đều tự động ghi nhật ký vào bảng dbo.crypto\_history và phát hiện ngay lập tức nếu bản mã bị chỉnh sửa dù chỉ một ký tự. Kết quả đạt PASS.



Tình huống bốn xây dựng và đối chiếu thuật toán mã dòng RC4 thông qua hai giai đoạn trộn mảng khóa KSA và sinh dòng khóa PRGA. Khi thử nghiệm với bản rõ chuẩn Plaintext và khóa Key, chương trình xuất ra chuỗi mã thập lục phân chính xác tuyệt đối là BBF316E8D940AF0AD3 và thực hiện phép toán XOR đảo ngược để khôi phục văn bản ban đầu. Kết quả đạt PASS.



Tình huống năm ứng dụng thư viện BouncyCastle để vận hành thuật toán mã khối Twofish 256 bit ở chế độ CBC với khóa được dẫn xuất qua hàm băm SHA-256 từ chuỗi Twofish@Lab2. Hệ thống sinh vectơ khởi tạo IV ngẫu nhiên dài 16 byte dưới dạng 32 ký tự thập lục phân, cho phép lưu trữ an toàn cả IV và bản mã Base64 vào tệp văn bản mà không làm lộ khóa bí mật, đồng thời khôi phục đầy đủ tham số để giải mã chính xác khi mở lại tệp. Kết quả đạt PASS.



Tình huống sáu thực hiện bài tập nâng cao một về chế độ vận hành AES-256-CTR biến đổi mã khối thành mã dòng không cần đệm dữ liệu. Ứng dụng tạo khóa ngẫu nhiên 256 bit cùng giá trị Nonce 96 bit dưới định dạng Base64, đảm bảo độ dài bản mã tương đương bản rõ, hỗ trợ lưu và khôi phục Nonce từ tệp tin và giải mã chính xác toàn bộ nội dung ban đầu. Kết quả đạt PASS.



Tình huống bảy triển khai bài tập nâng cao hai xây dựng ứng dụng mã hóa AES-256-CBC bằng ngôn ngữ Python trên giao diện Tkinter. Kịch bản aes\_cbc\_gui.py sử dụng lớp đệm chuẩn PKCS7 kết hợp sinh khóa 32 byte và IV 16 byte ngẫu nhiên an toàn, vượt qua toàn bộ bốn bài kiểm thử tự động trong chế độ self-test đối với văn bản tiếng Việt dài và phát hiện chính xác lỗi sai khóa giải mã. Kết quả đạt PASS.



Tình huống tám hoàn thiện giao diện điều hướng trung tâm frm\_Menu liên kết toàn bộ các chức năng mã hóa, quản lý kết nối cơ sở dữ liệu và truy xuất bảng lịch sử thao tác trực quan. Dữ liệu từ tất cả các phiên làm việc được đồng bộ hóa đầy đủ vào SQL Server, hiển thị chính xác trên giao diện frm\_LichSu cũng như kiểm chứng khớp hoàn toàn khi truy vấn trực tiếp trên SSMS. Kết quả đạt PASS.



Kết luận tổng thể cho thấy bài thực hành LAB 2 đã đáp ứng trọn vẹn toàn bộ yêu cầu kỹ thuật về cả mặt lý thuyết mật mã học lẫn kỹ năng phát triển phần mềm bảo mật đa nền tảng giữa Java, Python và SQL Server. Việc kết hợp linh hoạt các chế độ vận hành hiện đại như CBC, CTR, GCM cùng cơ chế dẫn xuất khóa PBKDF2, quản lý phiên bằng Ticket và tách biệt thông tin cấu hình nhạy cảm giúp đảm bảo tính bí mật, tính toàn vẹn dữ liệu và an toàn hệ thống ở mức độ cao.

