Họ và tên: Phạm Thị Minh Phi 

Mã số sinh viên: 1150070033 

Lớp: 11\_DHTMDT 

Tên bài thực hành: LAB 1 Mã hóa đối xứng cổ điển



Qua quá trình thực hành trên môi trường Visual Studio Code kết hợp công cụ quản lý dự án Maven và hệ quản trị cơ sở dữ liệu Microsoft SQL Server, chương trình mã hóa đối xứng cổ điển đã được xây dựng hoàn chỉnh và vận hành ổn định. Toàn bộ cấu trúc mã nguồn trong gói week\_01 đều biên dịch thành công, đảm bảo đầy đủ các tệp xử lý thuật toán, tệp giao diện Java Swing và tệp mô tả biểu mẫu theo đúng quy định. Hệ thống cửa sổ chính đóng vai trò điều hướng trung tâm giúp mở độc lập từng chức năng con mà không làm gián đoạn tiến trình chung của ứng dụng. 



Đối với sáu thuật toán mật mã cơ bản, chương trình thực hiện chính xác quy trình mã hóa và giải mã hai chiều, đồng thời hỗ trợ đọc và lưu trữ dữ liệu trực tiếp thông qua tệp văn bản định dạng UTF-8. 

Kết quả kiểm thử thực tế trùng khớp tuyệt đối với bộ dữ liệu chuẩn của bài thực hành. Cụ thể, thuật toán Caesar dịch chuyển bản rõ HELLO với khóa bằng 3 tạo ra bản mã KHOOR. Thuật toán Vigenère sử dụng khóa KEY chuyển đổi chuỗi HELLO thành RIJVS. Thuật toán Rail Fence xếp chữ theo đường dích dắc 3 hàng biến đổi chuỗi HELLO WORLD thành HOREL OLLWD. Thuật toán Playfair xây dựng ma trận khóa từ từ khóa KEYWORD mã hóa HELLOWORLD thành GYIZSCOKCFBU và khôi phục bản rõ kèm ký tự đệm X đúng nguyên lý. Thuật toán hoán vị cột với thứ tự đọc cột 2, 3, 1 chuyển chuỗi BAOMAT thành AAOTBM. Cuối cùng, thuật toán Autokey kết hợp khóa mồi KEY và chính bản rõ HELLO tạo ra bản mã RIJSS. Các trường hợp nhập khóa sai định dạng đều được bắt ngoại lệ và hiển thị cảnh báo rõ ràng. 



Ở phần bài tập nâng cao thứ nhất, biểu mẫu tổng hợp tích hợp thành công cả sáu thuật toán thông qua hộp chọn và tự động gợi ý khóa mẫu tương ứng cho từng phương pháp. Hệ thống kết nối thông suốt với cơ sở dữ liệu LAB1\_MaHoaCoDien trên Microsoft SQL Server bằng trình điều khiển JDBC thông qua tài khoản phân quyền lab1\_user. Mọi thao tác mã hóa hoặc giải mã đều được ghi nhận tự động vào bảng dbo.history bằng câu lệnh tham số hóa an toàn, sau đó truy xuất và hiển thị trực quan trên bảng dữ liệu giao diện cũng như kiểm chứng chính xác trong công cụ SSMS. 



Ở phần bài tập nâng cao thứ hai về phân tích và tấn công mật mã cổ điển, ba kỹ thuật phá mã hoạt động hiệu quả và cho ra kết quả phân tích chuẩn xác. Kỹ thuật vét cạn thử toàn bộ 26 khóa của mật mã Caesar trên bản mã KHOOR và tự động chấm điểm tần suất ngôn ngữ tiếng Anh để xác định chính xác khóa k bằng 3 cùng bản rõ HELLO. Kỹ thuật thống kê tần suất chữ cái trên đoạn bản mã dài PHHW PH DIWHU WKH WRJD SDUWB chỉ ra hai ký tự H và W chiếm tỷ lệ cao nhất là 21,7 phần trăm, từ đó suy luận chính xác độ dịch chuyển khóa k bằng 3. Kỹ thuật tấn công biết trước bản rõ dựa trên cặp dữ liệu HELLO và RIJVS tính toán chuẩn xác chuỗi khóa lặp lại KEYKE của mật mã Vigenère. Toàn bộ mã nguồn dự án, kịch bản tạo cơ sở dữ liệu an toàn không chứa mật khẩu và tài liệu báo cáo đã được đóng gói hoàn chỉnh để lưu trữ trên kho lưu trữ GitHub cá nhân. 

