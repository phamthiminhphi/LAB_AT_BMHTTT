# BÁO CÁO THỰC HÀNH BÀI LAB

Họ và tên: Phạm Thị Minh Phi

Mã số sinh viên: 1150070033

Lớp: 11_DHTMDT

Tên bài thực hành: Lab 4 Khảo sát quét cổng mạng và phân tích dấu vết hệ thống với Nmap

Phiên bản môi trường thực nghiệm bao gồm máy tấn công vận hành hệ điều hành Kali Linux trang bị công cụ Nmap phiên bản bảy chấm chín mươi chín. Máy chủ mục tiêu vận hành hệ điều hành Microsoft Windows Server 2022 bản phát hành chính thức được cấu hình trong phân vùng mạng cô lập.

Cách dựng môi trường thực nghiệm được triển khai đồng bộ trên nền tảng ảo hóa VMware Workstation. Cả hai máy ảo Kali Linux và Windows Server 2022 đều được thiết lập gắn chung vào card mạng tùy chỉnh VMnet1 theo chế độ Host Only nhằm tạo không gian mạng cục bộ cách ly hoàn toàn với Internet. Máy Windows Server 2022 được đặt địa chỉ IP là 192.168.199.128, trong khi máy Kali Linux nhận địa chỉ IP cùng dải mạng. Người kiểm thử tiến hành kiểm tra khả năng truyền thông hai chiều giữa hai máy và cấu hình tường lửa Windows Defender để phục vụ công tác rà soát trước và sau khi làm cứng hệ thống.

Các tình huống kỹ thuật đã thực hiện và kết quả đánh giá chi tiết:

Tình huống một triển khai kỹ thuật quét TCP Connect và kỹ thuật quét bán mở SYN để đối chiếu cơ chế bắt tay ba bước cũng như phát hiện các cổng dịch vụ đang lắng nghe trên máy chủ. Kết quả đạt PASS.

Tình huống hai thực hiện các kỹ thuật quét biến thể như FIN, Xmas và NULL nhằm khảo sát cách ngăn xếp mạng TCP IP của Windows Server phản hồi với các tổ hợp cờ bất thường. Kết quả đạt PASS.

Tình huống ba thực hiện kỹ thuật quét ACK để thăm dò cơ chế lọc gói tin và phân tích xem tường lửa có hoạt động theo trạng thái hay không. Kết quả đạt PASS.

Tình huống bốn triển khai quét rà soát hai mươi cổng UDP thông dụng để quan sát cơ chế phản hồi phi kết nối và hiện tượng giới hạn tốc độ gói tin ICMP của hệ điều hành. Kết quả đạt PASS.

Tình huống năm thực thi quét nhận diện sâu phiên bản dịch vụ và phân tích dấu vết mạng để nhận dạng chính xác hệ điều hành Microsoft Windows Server 2022. Kết quả đạt PASS.

Tình huống sáu chạy kịch bản quét nâng cao kết hợp toàn diện để bóc tách thông tin định tuyến mạng, tiêu đề ứng dụng web và thông số NetBIOS. Kết quả đạt PASS.

Tình huống bảy sử dụng tập lệnh chuyên dụng NSE để trích xuất cấu hình bảo mật tầng ứng dụng của dịch vụ chia sẻ tệp SMB và rà soát độ lệch đồng hồ hệ thống. Kết quả đạt PASS.

Tình huống tám thực hiện kiểm tra lỗ hổng bảo mật thực thi mã từ xa MS17-010 và xác nhận hệ thống an toàn do đã vô hiệu hóa hoàn toàn giao thức cũ SMBv1. Kết quả đạt PASS.

Tình huống chín xuất hồ sơ kết quả kiểm thử đồng thời ra các định dạng chuẩn bao gồm văn bản thô, tệp XML có cấu trúc, tệp grepable phục vụ lọc nhanh và chuyển đổi sang báo cáo HTML trực quan. Kết quả đạt PASS.

Tình huống mười thực hiện kích hoạt lại tường lửa toàn diện trên Windows Server để đối chiếu trạng thái các cổng mạng trước và sau khi làm cứng hệ thống. Kết quả đạt PASS.

Kết luận tổng thể: Toàn bộ mười tình huống thực nghiệm trong bài lab đều hoàn thành chính xác theo đúng yêu cầu kỹ thuật và đạt kết quả đánh giá chung là PASS.