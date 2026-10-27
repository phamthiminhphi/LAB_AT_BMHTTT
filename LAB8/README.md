Họ và tên: Phạm Thị Minh Phi 

Mã số sinh viên: 1150070033 

Lớp: 11\_DHTMDT 

Tên bài thực hành: Báo cáo thực hành kỹ thuật DNS Spoofing và DNS Cache Poisoning trong môi trường mạng cô lập



Phiên bản môi trường thực hành được triển khai trên nền tảng ảo hóa VMware Workstation với các phân hệ điều hành chuyên biệt phục vụ kiểm thử an toàn mạng.

Máy tấn công kiêm máy chủ phân giải tên miền ủy quyền sử dụng Kali Linux phiên bản 2024.1 tích hợp bộ công cụ Scapy phiên bản 2.5.0, trình bắt gói tin Tcpdump phiên bản 4.99.4 và môi trường thông dịch Python 3.11. 

Máy chủ phân giải tên miền đệ quy kiêm máy trạm kiểm thử cục bộ chạy trên hệ điều hành Ubuntu Server 24.04 LTS với dịch vụ máy chủ tên miền BIND9 phiên bản 9.18.39 cùng các tiện ích mạng Bind9-utils như Dig, Rndc và gói phần mềm phân giải Systemd-resolved. 

Hệ thống định tuyến và kiểm soát an ninh biên do thiết bị tường lửa mềm pfSense Community Edition phiên bản 2.7.2 đảm nhiệm với đầy đủ chức năng quản lý quy tắc lọc gói tin. 



Cách dựng môi trường mạng thực hành được thiết kế theo cấu trúc cô lập tuyệt đối nhằm ngăn chặn các luồng dữ liệu giả mạo phát tán ra ngoài hạ tầng thực tế. Tường lửa pfSense kết nối qua các phân đoạn mạng ảo VMnet tùy chỉnh, trong đó phân vùng thử nghiệm mang tên DNSLAB hoạt động theo cơ chế mạng nội bộ Host-Only tương ứng dải địa chỉ 10.9.0.0/24. Địa chỉ cổng ngõ mặc định của phân vùng này là 10.9.0.1 được gán trên giao diện mạng của pfSense, còn máy chủ Ubuntu Server nhận địa chỉ mạng tĩnh 10.9.0.10/24. Trên máy tấn công Kali Linux, giao diện mạng eth0 được cấp địa chỉ tĩnh 10.9.0.200/24 đồng thời cấu hình thêm địa chỉ mạng phụ 172.16.6.20/24 để đóng vai trò máy chủ ủy quyền ngoài. Trên pfSense, toàn bộ các quy tắc lọc gói tin của giao diện DNSLAB được thiết lập nghiêm ngặt, chỉ cho phép luồng lưu lượng truy vấn tên miền cổng dịch vụ 53 đi sang địa chỉ máy chủ ủy quyền và cấm tuyệt đối mọi lưu lượng rò rỉ ra các vùng mạng khác. Trước khi tiến hành thực nghiệm, dịch vụ BIND9 trên máy chủ Ubuntu được kiểm tra tính hợp lệ của cú pháp cấu hình và các tệp cơ sở dữ liệu vùng thông qua hai công cụ named-checkconf và named-checkzone bảo đảm trạng thái nạp dữ liệu hoàn hảo. 



Tình huống một tập trung kiểm thử hành vi thao túng tệp cấu hình phân giải tĩnh nội bộ trên máy trạm thông qua việc chèn bản ghi điều hướng tên miền www.lab9.test trỏ về địa chỉ máy chủ giả mạo 10.9.0.200 trong tệp hosts của hệ điều hành Ubuntu Server. Khi kiểm tra bằng tiện ích getent hosts và lệnh curl, hệ thống phản hồi trang web giả mạo do cơ chế NSSwitch ưu tiên tra cứu tệp cục bộ trước, tuy nhiên lệnh dig gửi truy vấn trực tiếp đến máy chủ BIND9 tại địa chỉ 10.9.0.10 vẫn trả về chính xác địa chỉ máy chủ hợp lệ 172.16.6.10 do công cụ này bỏ qua lớp ánh xạ của hệ điều hành, ghi nhận Kết quả đạt PASS. 



Tình huống hai hướng tới mục tiêu giả mạo phản hồi tên miền trực tiếp trên đường truyền mạng nhắm vào máy nạn nhân bằng cách chèn gói tin giả mạo chứa mã phiên và cổng dịch vụ trùng khớp. Về công tác chuẩn bị, hệ thống đã cấu hình đầy đủ script mô phỏng máy chủ ủy quyền phản hồi trễ 1.5 giây trên Kali Linux tại địa chỉ 172.16.6.20 cổng 53, kích hoạt tiến trình tcpdump lắng nghe cổng dịch vụ 53 trên card mạng eth0, khởi chạy thành công kịch bản tấn công dns\_spoof\_lab9.py ở chế độ victim trên card mạng eth0 và kiểm tra phân giải đệ quy chuẩn trên Ubuntu Server. Dù quá trình chuẩn bị hạ tầng kỹ thuật đã hoàn tất trọn vẹn, tình huống này không thể ghi nhận thành công và kết thúc với Kết quả FAIL. Nguyên nhân kỹ thuật dẫn đến sự hạn chế này là do mô hình thực nghiệm gom chung máy nạn nhân và máy chủ BIND9 trên cùng một hệ điều hành máy ảo Ubuntu Server. Toàn bộ tiến trình truy vấn từ câu lệnh dig đến dịch vụ named diễn ra khép kín trong ngăn xếp mạng nội bộ qua giao diện lặp loopback của nhân Linux, các gói tin này không bao giờ được chuyển đổi thành khung truyền Ethernet vật lý để phát ra switch ảo, dẫn tới việc card mạng trên máy Kali Linux dù đặt ở chế độ bắt lén mở rộng vẫn hoàn toàn không thể bắt được gói tin truy vấn để giải mã và chế tạo phản hồi giả mạo chen ngang. 



Tình huống ba triển khai tấn công đầu độc bộ nhớ đệm của máy chủ phân giải đệ quy BIND9 đối với tên miền ngoài www.external.lab9.test bằng cách lợi dụng độ trễ của máy chủ ủy quyền hợp lệ để gửi phản hồi giả mạo trước. Khi máy chủ Ubuntu Server gửi truy vấn đệ quy ra switch ảo, máy tấn công Kali Linux đã bắt được gói tin, trích xuất tức thì cổng mạng tạm thời cùng mã định danh phiên mười sáu bit và gửi ngược lại một phản hồi DNS giả mạo chứa địa chỉ 10.9.0.200 trước khi phản hồi thật kịp về. Máy chủ BIND9 chấp nhận gói tin giả mạo này, trả kết quả cho người dùng đồng thời lưu bản ghi độc hại vào bộ nhớ đệm RAM với thời gian sống 120 giây, được kiểm chứng trực tiếp qua câu lệnh dig và thao tác xuất cơ sở dữ liệu đệm bằng lệnh rndc dumpdb, ghi nhận Kết quả đạt PASS. 



Tình huống bốn thực hiện quy trình khôi phục trạng thái toàn vẹn của hệ thống sau sự cố ô nhiễm bộ nhớ đệm bằng cách dừng hoàn toàn kịch bản tấn công trên Kali Linux, thực thi chuỗi lệnh rndc flush và resolvectl flush-caches trên Ubuntu Server để dọn sạch bản ghi tạm, sau đó gửi lại truy vấn dig và xác nhận kết quả phân giải đã trở về chính xác địa chỉ máy chủ gốc 172.16.6.10 với thời gian sống 60 giây, ghi nhận Kết quả đạt PASS. 



Kết luận tổng thể cho thấy bài thực hành đã giúp làm sáng tỏ toàn bộ cơ chế phân giải tên miền từ cấp độ trạm đầu cuối đến máy chủ đệ quy và máy chủ ủy quyền trong môi trường mạng thực tế. Bài kiểm thử đã chỉ rõ sự khác biệt mang tính cốt lõi giữa việc chỉnh sửa cấu hình máy trạm đơn lẻ và kỹ thuật đầu độc bộ nhớ đệm máy chủ tập trung, từ đó khẳng định rằng các giải pháp phòng thủ hiện đại như chữ ký số DNSSEC, cơ chế ngẫu nhiên hóa cổng mạng và các chính sách kiểm soát luồng dữ liệu nghiêm ngặt trên tường lửa là những yêu cầu bắt buộc nhằm bảo đảm tính toàn vẹn tuyệt đối cho hạ tầng dịch vụ mạng.



