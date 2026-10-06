Họ và tên: Phạm Thị Minh Phi

Mã số sinh viên: 1150070033

Lớp: 11\_DH\_TMDT

Tên bài thực hành: Cấu hình Firewall pfSense NAT và phân vùng DMZ an toàn



Phiên bản môi trường thực hành được triển khai trên nền tảng ảo hóa VMware Workstation Pro. Hệ thống tường lửa trung tâm sử dụng pfSense phiên bản hai chấm bảy chấm hai chạy trên nhân FreeBSD. Máy chủ dịch vụ đóng vai trò bộ điều khiển vùng miền nội bộ sử dụng hệ điều hành Windows Server hai nghìn không trăm hai mươi hai Standard. Máy tấn công và kiểm thử an ninh kiêm máy chủ dịch vụ vùng bán quân sự sử dụng Kali Linux phiên bản hai nghìn không trăm hai mươi bốn. Máy trạm vật lý của người dùng sử dụng Windows mười một làm nguồn truy cập ngoại mạng từ cổng WAN. Các công cụ kiểm thử mạng chính bao gồm tiện ích ping, công cụ kiểm tra bản ghi DNS nslookup, công cụ curl, máy chủ web Python ba tích hợp và trình duyệt Google Chrome.



Mô hình mạng được xây dựng phân tách độc lập qua ba vùng bảo mật riêng biệt kết nối qua ba card mạng ảo của pfSense. Cổng WAN gắn card mạng số một ở chế độ Bridged nhận địa chỉ IP mười chín hai chấm một sáu tám chấm một trăm chấm bốn trực tiếp từ mạng vật lý ngoài. Cổng LAN gắn card mạng số hai ở chế độ Host-Only cấp phát dải mạng mười chấm không chấm không chấm không phần tám với gateway mười chấm không chấm không chấm một, trong đó Windows Server nhận địa chỉ tĩnh mười chấm không chấm không chấm hai. Cổng DMZ gắn card mạng số ba ở chế độ LAN Segment đặt tên DMZ tạo dải mạng cô lập một bảy hai chấm một sáu chấm không chấm không phần mười sáu với gateway một bảy hai chấm một sáu chấm không chấm một, trong đó máy trạm Kali Linux nhận IP một bảy hai chấm một sáu chấm không chấm hai. Trước khi cấu hình tường lửa, Windows Server được nạp luật netsh cho phép giao thức ICMPv4 dải ngoài gửi vào nhằm loại bỏ rủi ro dương tính giả từ tường lửa nội bộ của hệ điều hành.



Tình huống một triển khai chính sách lọc gói tin chỉ cho phép lưu lượng duyệt web và phân giải tên miền từ mạng LAN đồng thời chặn toàn bộ yêu cầu ping ICMP. Quản trị viên vô hiệu hóa hai rule mở toàn bộ mặc định của pfSense, sau đó thiết lập luật cho phép cổng năm mươi ba đối với giao thức TCP và UDP cùng luật cho phép cổng tám mươi và bốn trăm bốn mươi ba đối với giao thức TCP. Kiểm thử thực tế cho thấy máy trạm LAN phân giải tên miền và duyệt web thành công nhưng lệnh ping ra Internet bị mất gói hoàn toàn theo nguyên lý Default Deny. Kết quả đạt PASS



Tình huống hai kiểm soát quyền truy cập Internet có chọn lọc trong mạng nội bộ dựa trên địa chỉ nguồn. Quản trị viên tạo luật Pass cho phép địa chỉ IP tĩnh mười chấm không chấm không chấm hai của máy chủ Windows Server truy cập mọi dịch vụ ra bên ngoài, đồng thời tạo ngay bên dưới một luật Block chặn toàn bộ dải mạng LAN subnets. Nhờ nguyên tắc so khớp luật đầu tiên First Match của tường lửa, Windows Server ra ngoài Internet bình thường trong khi các máy trạm khác cùng dải LAN bị chặn gói hoàn toàn. Kết quả đạt PASS



Tình huống ba thiết lập chính sách bảo vệ mạng nội bộ bằng cách cô lập toàn diện vùng DMZ khỏi mạng LAN nhưng vẫn duy trì kết nối Internet. Trên giao diện luật của cổng DMZ, một luật Block được đặt ở vị trí ưu tiên cao nhất để chặn mọi lưu lượng có nguồn từ DMZ subnets hướng tới đích LAN subnets, tiếp theo là một luật Pass cho phép DMZ đi ra ngoài Internet. Thao tác kiểm thử từ máy trạm Kali Linux trong DMZ chứng minh lệnh ping hướng tới Windows Server bị chặn đứng trong khi lệnh ping và truy cập ra Internet vẫn thông suốt. Kết quả đạt PASS



Tình huống bốn xuất bản dịch vụ web nội bộ từ vùng DMZ ra Internet thông qua kỹ thuật NAT Port Forward. Cổng WAN của pfSense được bỏ kích hoạt tính năng chặn dải mạng riêng tư, sau đó một quy tắc biên dịch địa chỉ đích được tạo để bắt luồng TCP cổng tám nghìn không trăm tám mươi trên địa chỉ WAN rồi chuyển tiếp vào máy chủ Kali Linux tại địa chỉ một bảy hai chấm một sáu chấm không chấm hai cổng tám mươi có liên kết luật lọc tự động. Người dùng tại máy vật lý truy cập thành công giao diện web máy chủ DMZ qua địa chỉ WAN mà không làm lộ kiến trúc mạng nội bộ. Kết quả đạt PASS



Tình huống năm kích hoạt cơ chế ghi nhật ký vi phạm và giám sát trực tiếp trên tường lửa pfSense. Quản trị viên bật tính năng Log trên luật chặn DMZ truy cập LAN, sau đó kích hoạt lưu lượng ping từ Kali Linux tới địa chỉ máy chủ trong LAN để ép tường lửa thực thi hành động hủy gói tin. Bảng nhật ký Firewall Logs tại mục System Logs ghi nhận chuẩn xác dòng sự kiện có biểu tượng dấu X đỏ kèm đầy đủ thông tin về thời gian, giao diện DMZ, địa chỉ nguồn, địa chỉ đích và giao thức vi phạm. Kết quả đạt PASS



Bài thực hành đã hoàn thành toàn diện các yêu cầu kỹ thuật về phòng thủ mạng trên thiết bị tường lửa pfSense. Quá trình triển khai giúp làm rõ bản chất hoạt động của quy tắc lọc gói tin theo cơ chế First Match, sự khác biệt cốt lõi giữa cơ chế biên dịch địa chỉ NAT và lớp bảo vệ của Firewall Rule, cũng như tầm quan trọng của việc quy hoạch vùng mạng DMZ nhằm khoanh vùng rủi ro khi cung cấp các dịch vụ công khai ra thế giới bên ngoài.

