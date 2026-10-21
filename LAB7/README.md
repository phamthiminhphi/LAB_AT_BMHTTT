Họ và tên: Phạm Thị Minh Phi 
Mã số sinh viên: 1150070033 
Lớp: 11_DHTMDT 
Tên bài thực hành: Báo cáo thực hành triển khai chính sách lọc nội dung Web Proxy và Tường lửa pfSense

Phiên bản môi trường Môi trường thực hành được triển khai đồng thời trên ba máy ảo độc lập qua nền tảng ảo hóa VMware Workstation. Thiết bị định tuyến và tường lửa trung tâm sử dụng hệ điều hành pfSense phiên bản 2.7.2 đóng vai trò gateway kiểm soát luồng mạng. Máy chủ dịch vụ ủy quyền web sử dụng hệ điều hành Ubuntu Server phiên bản 22.04 LTS, tích hợp dịch vụ lọc nội dung E2Guardian phiên bản 5.3.5, dịch vụ proxy đệm Squid phiên bản 6.14 cùng công cụ quét mã độc ClamAV daemon phiên bản 0.103.11. Máy chủ đích DMZ đóng vai trò cung cấp website và tài nguyên tải về sử dụng hệ điều hành Kali Linux phiên bản 2024.1, vận hành dịch vụ máy chủ web Nginx phiên bản 1.24.0 cùng các công cụ curl và dd để hỗ trợ kiểm thử. 
Cách dựng môi trường Hệ thống mạng được thiết lập hoàn toàn biệt lập thông qua ba mạng ảo Custom Host-Only nhằm mô phỏng phân vùng bảo mật thực tế. Vùng mạng DMZ sử dụng switch ảo VMnet2 với dải mạng 172.16.6.0/24, gán địa chỉ gateway 172.16.6.1 trên cổng mạng của pfSense và địa chỉ tĩnh 172.16.6.10 cho máy chủ dịch vụ web Kali Linux. Phân vùng mạng nội bộ LAN Client kết nối qua switch ảo VMnet3 với dải mạng 10.6.0.0/24, trong đó cổng mạng pfSense nhận địa chỉ gateway 10.6.0.1 và máy chủ web proxy nhận địa chỉ tĩnh 10.6.0.30. Cổng ngoài WAN của pfSense sử dụng chế độ NAT qua VMnet8 để quản trị và phân giải dịch vụ mạng. Trước khi thực hành, bảng phân giải tên miền nội bộ được khai báo trên tệp hosts của các máy trạm trỏ về máy chủ web DMZ, đồng thời áp dụng chính sách tường lửa trên pfSense chỉ cho phép duy nhất địa chỉ IP máy proxy kết nối sang vùng DMZ qua cổng 80 và 443. 

Các tình huống đã thực hiện
Tình huống một thực hiện kiểm thử truy cập trang web thông thường normal.lab7.test nhằm đánh giá khả năng phân luồng cơ bản, trong đó lệnh kiểm thử qua proxy cổng 8080 phản hồi thành công mã trạng thái HTTP 200 OK do nằm trong danh sách ngoại lệ, còn khi truy cập trực tiếp thì kết nối bị tường lửa pfSense ngắt thời gian chờ đúng theo yêu cầu, Kết quả đạt PASS. 

Tình huống hai thực hiện kiểm tra chính sách chặn tuyệt đối các tên miền địa lý mô phỏng china.lab7.test và mideast.lab7.test qua việc khai báo vào danh sách cấm bannedsitelist của E2Guardian, kết quả cả hai địa chỉ đều bị từ chối truy cập và trả về trang khóa hoàn toàn không có liên kết xác nhận tạm thời, Kết quả đạt PASS. 

Tình huống ba kiểm thử chính sách cấm truy cập danh mục tìm kiếm việc làm đối với trang jobs.lab7.test, hệ thống ghi nhận mã phản hồi lỗi 403 Forbidden kèm cờ từ chối trên nhật ký vận hành access.log của E2Guardian, Kết quả đạt PASS. 

Tình huống bốn áp dụng cơ chế cảnh báo có điều kiện cho các tên miền vũ khí, chính trị và tôn giáo qua cấu hình bypass 300 giây trong tệp e2guardianf1.conf, giao diện phản hồi hiển thị thành công thông báo cảnh báo kèm đường dẫn xác nhận Acknowledge mang mã khóa tạm thời, Kết quả đạt PASS. 

Tình huống năm thực hiện kiểm tra tên miền giáo dục education.lab7.test khi được đưa vào danh sách trắng exceptionsitelist, toàn bộ yêu cầu tải trang qua cổng 8080 đều phản hồi thành công mã 200 OK ngay lập tức mà không gặp bất kỳ cản trở nào, Kết quả đạt PASS. 

Tình huống sáu kiểm thử chính sách kiểm soát khung giờ giải trí 60 phút thông qua cấu hình Schedule gán vào rule tường lửa trên pfSense, lưu lượng truy cập tới entertainment.lab7.test và games.lab7.test được thông tuyến trong khung giờ hoạt động và tự động ngắt kết nối khi hết giờ, Kết quả đạt PASS. 

Tình huống bảy kiểm tra cơ chế cảnh báo đối với dịch vụ truyền thông trực tuyến streaming.lab7.test, hệ thống trả về trang cảnh báo và cấp quyền tiếp tục truy cập sau khi người dùng bấm xác nhận qua tham số GUBYPASS, Kết quả đạt PASS. 

Tình huống tám thực hiện chặn cứng trò chơi điện tử chỉ định blockedgame.lab7.test bằng việc khai báo vào bannedsitelist độc lập với chính sách khung giờ, kết quả trang web bị từ chối truy cập toàn bộ thời gian, Kết quả đạt PASS. 

Tình huống chín tiến hành kiểm tra khả năng phát hiện và ngăn chặn mã độc của plugin clamdscan khi tải tệp tin eicar.com.txt từ máy chủ, tuy nhiên tiến trình gặp lỗi đường truyền và đường dẫn tài nguyên dẫn đến trạng thái lỗi mạng NETERROR và tệp chưa được bộ máy ClamAV nhận diện thành công mã độc INFECTED, Kết quả đạt FAIL. 

Tình huống mười kiểm thử quy tắc trần dung lượng tải về reply body max size 100 MB cấu hình trong dịch vụ Squid, kết quả tệp tin mẫu 100MB.bin được tải về trọn vẹn trong khi tệp tin 110MB.bin bị proxy ngắt luồng và chỉ trả về trang thông báo lỗi quá kích thước, Kết quả đạt PASS. 

Kết luận tổng thể Bài thực hành đã giúp làm rõ nguyên lý phối hợp giữa thiết bị định tuyến tường lửa tầng mạng và hệ thống proxy lọc nội dung tầng ứng dụng. Hệ thống đã hiện thực hóa hầu hết các yêu cầu cốt lõi về kiểm soát tên miền, phân định danh mục cấm tuyệt đối và cảnh báo có điều kiện, quản lý lưu lượng theo khung thời gian thực và giới hạn băng thông kích thước tệp tải về. Tuy vậy, kịch bản quét mã độc tệp tin qua giao thức kết nối proxy vẫn còn tồn đọng lỗi cấu hình socket kết nối dịch vụ quét nền nên chưa thể hoàn tất chỉ tiêu, đồng thời bài lab chưa kịp triển khai các nội dung mở rộng liên quan đến kiểm duyệt gói tin mã hóa chuyên sâu. Đây là cơ sở thực tế quan trọng để tiếp tục tối ưu hóa tham số dịch vụ và kiện toàn giải pháp an toàn thông tin mạng trong các bài thực hành tiếp theo.