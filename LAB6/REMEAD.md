Họ và tên: Phạm Thị Minh Phi
Mã số sinh viên: 1150070033
Lớp: 11\_DH\_TMDT

Tên bài thực hành: Báo cáo thực hành Tường lửa pfSense và Kiểm soát chính sách truy cập Lab 6



Phiên bản môi trường

Hệ thống tường lửa trung tâm sử dụng hệ điều hành pfSense phiên bản Community Edition 2.7.2 đóng vai trò bộ định tuyến và thiết bị lọc gói có trạng thái. Phía mạng nội bộ sử dụng máy khách chạy Windows 10 Pro phiên bản 64 bit được dùng để luân phiên gán các địa chỉ mạng tĩnh kiểm thử chính sách. Vùng máy chủ dịch vụ DMZ sử dụng hệ điều hành Kali Linux phiên bản 2024.1 được cấu hình các dịch vụ máy chủ web Python HTTP Server và Nginx để lắng nghe các cổng dịch vụ web. Trình duyệt Microsoft Edge và công cụ dòng lệnh cURL trên Windows cùng tiện ích ping trên Kali Linux được sử dụng làm công cụ tạo và kiểm tra lưu lượng mạng.



Cách dựng môi trường

Mô hình bài thực hành được triển khai cô lập thông qua các bộ chuyển mạch ảo nội bộ Host-Only trên nền tảng ảo hóa VMware Workstation. Thiết bị pfSense được cấu hình bốn giao diện mạng riêng biệt gồm cổng WAN kết nối mạng ngoài, cổng MGMT dải 192.168.6.0/24 phục vụ truy cập giao diện cấu hình quản trị, cổng LAN\_CLIENT dải 10.6.0.0/24 dành cho các máy trạm người dùng và cổng DMZ\_SERVER dải 172.16.6.0/24 dành cho vùng máy chủ công cộng. Máy Windows 10 được gắn vào mạng LAN\_CLIENT với địa chỉ kiểm thử ban đầu là 10.6.0.21 tương ứng máy A và chuyển đổi sang 10.6.0.22 tương ứng máy B. Máy Kali Linux được gắn vào mạng DMZ\_SERVER với địa chỉ chính 172.16.6.10 tương ứng trang web chung, đồng thời sử dụng cơ chế gán nhãn mạng phụ trên giao diện mạng để tạo thêm các địa chỉ 172.16.6.20 đại diện trang tuyển dụng, 172.16.6.30 đại diện mạng xã hội và 172.16.6.40 đại diện trang hạn chế. Toàn bộ các dịch vụ trên Kali Linux được khởi động lắng nghe trước khi áp dụng các bộ luật tường lửa.



Các tình huống đã thực hiện

Tình huống một thực hiện kiểm soát truy cập chọn lọc cho máy A thuộc mạng nội bộ, thiết lập luật chặn kết nối HTTP cổng 80 tới địa chỉ máy chủ tuyển dụng 172.16.6.20 nằm trên luật cho phép truy cập máy chủ thông thường 172.16.6.10, kết quả thực tế máy A duyệt web 172.16.6.10 hiển thị nội dung thành công và mở trang 172.16.6.20 nhận thông báo lỗi hết thời gian phản hồi, kết quả đạt PASS



Tình huống hai thực hiện cấm danh mục trang hạn chế cho toàn bộ nhóm máy trạm thông qua định danh nhóm địa chỉ POLICY\_CLIENTS gồm cả máy A và máy B, cấu hình luật chặn cổng 80 tới địa chỉ 172.16.6.40, kết quả thực tế cả hai địa chỉ 10.6.0.21 và 10.6.0.22 đều bị ngắt kết nối hết thời gian phản hồi khi vào trang 172.16.6.40 trong khi vẫn mở được trang 172.16.6.10 bình thường, kết quả đạt PASS



Tình huống ba thực hiện chính sách kiểm soát theo lịch trình thời gian đối với máy B khi truy cập trang mạng xã hội 172.16.6.30, kết hợp luật cho phép gắn lịch TEST\_NOW nằm trên luật chặn dự phòng cố định, kết quả thực tế trong khung giờ quy định máy B tải trang web bình thường và ngay khi khung giờ kết thúc luật chặn lập tức làm gián đoạn kết nối, kết quả đạt PASS



Tình huống bốn thực hiện giới hạn dịch vụ ở tầng giao vận bằng cách cấu hình luật cho phép giao thức HTTPS cổng 443 và luật chặn giao thức HTTP cổng 80 đối với máy chủ 172.16.6.10, kết quả thực tế yêu cầu truy cập từ trình duyệt theo giao thức HTTP bị hủy gói tin và báo lỗi không thể truy cập, kết quả đạt PASS



Tình huống năm thực hiện chính sách cô lập vùng máy chủ DMZ khỏi mạng nội bộ LAN, cấu hình luật chặn toàn bộ lưu lượng có nguồn từ DMZ\_SERVER đi tới đích LAN\_CLIENT, kết quả thực tế máy Windows 10 từ LAN chủ động mở trang web DMZ tải dữ liệu thành công nhưng máy Kali Linux từ DMZ ping kiểm tra sang địa chỉ 10.6.0.21 nhận tỷ lệ mất gói tin 100 phần trăm, kết quả đạt PASS



Tình huống sáu và bảy thực hiện phân tích cơ chế từ chối gói tin, xem nhật ký tường lửa và quản lý bảng trạng thái, kiểm tra sự khác biệt giữa phương thức chặn âm thầm gây lỗi hết thời gian phản hồi và phương thức bác bỏ phản hồi cờ ngắt kết nối ngay lập tức, nhật ký hệ thống ghi nhận đầy đủ bản ghi dấu chéo đỏ chứa thông tin địa chỉ nguồn và đích, thao tác làm sạch bảng trạng thái được kích hoạt thành công để buộc các phiên duyệt web áp dụng luật mới tức thì, kết quả đạt PASS





