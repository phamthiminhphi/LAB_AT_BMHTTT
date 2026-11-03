Họ và tên: Phạm Thị Minh Phi 

Mã số sinh viên: 1150070033 

Lớp: 11\_DHTMDT

&#x20;Tên bài thực hành: LAB 10 Remote DNS Cache Poisoning trong môi trường pfSense cô lập

Phiên bản môi trường

Hệ thống mạng phòng thí nghiệm được triển khai đồng bộ trên nền tảng ảo hóa với máy chủ tường lửa định tuyến chạy hệ điều hành pfSense . Máy trạm tấn công sử dụng hệ điều hành Kali Linux với công cụ bắt gói tin tcpdump và môi trường thực thi Python ba tích hợp sẵn thư viện dnspython. Máy chủ phân giải tên miền cùng các vai trò nạn nhân và máy chủ thẩm quyền được hợp nhất trên hệ điều hành Ubuntu Server , trong đó dịch vụ tên miền nòng cốt là BIND, kết hợp cùng các kịch bản mô phỏng kiểm thử được viết bằng ngôn ngữ Python ba.

Cách dựng môi trường

Vì GB máy thật không mở được quá ba máy ảo nên hạ tầng sẽ được quy hoạch tối ưu trên ba máy ảo thông qua việc phân chia các bộ chuyển mạch ảo độc lập nhằm đáp ứng yêu cầu cô lập tài nguyên phần cứng. Router pfSense quản lý bốn phân vùng mạng riêng biệt gồm cổng ra Internet sử dụng cơ chế dịch địa chỉ mạng NAT qua dải 192. 168. 135.0/24, phân vùng tấn công ATTACKNET liên kết với mạng nội bộ máy chủ ảo Custom VMnet 6 tại dải 10.10.0.0/24, phân vùng nội bộ DNSLAB liên kết với Custom VMnet 5 tại dải 10.9.0.0/24 và phân vùng vùng an toàn phi quân sự DMZ liên kết với Custom VMnet 7 tại dải 172.16.6.0/24 . Máy Kali Linux được cấp địa chỉ tĩnh 10.10.0.200 với cổng ngõ 10.10.0.1 và tắt hoàn toàn chế độ nhận toàn bộ khung truyền Promiscuous Mode. Máy Ubuntu Server gánh đa nhiệm các vai trò bằng cách gán giao diện mạng ens37 vào dải DNSLAB nhận địa chỉ chính 10.9.0.10 làm máy chủ phân giải DNS và địa chỉ phụ 10.9.0.100 làm máy khách nạn nhân, đồng thời giao diện mạng ens 38 được tách vào một không gian tên mạng riêng biệt mang tên ns\_dmz để nhận địa chỉ 172.16.6.20 làm máy chủ thẩm quyền kết hợp tiếp sức chạy đua và địa chỉ 172.16.6.10  làm máy chủ trang web gốc. Công tác tiền chuẩn bị được tiến hành chỉn chu thông qua việc lọc bỏ triệt để các mã điều khiển đầu cuối ẩn bằng sed trên kịch bản máy chủ thẩm quyền và định tuyến tạm qua card mạng NAT để hoàn tất cài đặt gói python ba dnspython trước khi khóa cô lập mạng.

Các tình huống đã thực hiện

Tình huống một triển khai cấu hình hạ tầng mạng trên pfSense và áp dụng kỹ thuật phân tách không gian tên mạng ns\_dmz cho card ens ba mươi tám trên Ubuntu Server, sau đó kiểm tra độ thông suốt tầng liên kết dữ liệu và tầng mạng từ không gian tên này đến cổng ngõ một bảy hai chấm mười sáu chấm sáu chấm một của tường lửa, kết quả xác nhận các gói tin phản hồi thành công và không thất thoát gói, Kết quả đạt PASS

Tình huống hai khởi chạy kịch bản máy chủ thẩm quyền auth\_dns\_relay\_lab10.py bên trong không gian tên mạng ns\_dmz, kích hoạt tiến trình lắng nghe trên socket UDP cổng năm mươi ba và cổng năm mươi ba trăm của địa chỉ một bảy hai chấm mười sáu chấm sáu chấm hai mươi, đồng thời khởi động dịch vụ BIND chín trên địa chỉ mười chấm chín chấm không chấm mười tại không gian tên gốc, Kết quả đạt PASS

Tình huống ba thực hiện đo đạc phân giải cơ sở từ vai trò máy khách nạn nhân và truy vấn chuyển tiếp từ máy chủ BIND chín sang máy chủ thẩm quyền với tên miền www.example.lab10.test, tuy nhiên các lệnh dig đều bị quá thời gian chờ do xung đột bảng địa chỉ MAC của bộ chuyển mạch ảo VMware và cơ chế lọc trạng thái bất đối xứng của tường lửa pfSense khi dồn nhiều phân vùng lên cùng một máy ảo vật lý, Kết quả đạt FAIL

Tình huống bốn kiểm chứng tính chất tấn công từ xa bằng việc thực hiện bắt gói tin UDP cổng năm mươi ba trên giao diện eth không của máy tấn công Kali Linux, màn hình ghi nhận Kali chỉ thấy các gói tin yêu cầu thời gian do chính nó phát ra và hoàn toàn không thể bắt được bất kỳ lưu lượng nào trao đổi giữa dải DNSLAB và DMZ nhờ sự cô lập chặt chẽ của router pfSense, Kết quả đạt PASS

Tình huống năm kích hoạt kịch bản tấn công suy đoán mã nhận diện giao dịch từ xa Kaminsky và theo dõi các nhật ký trạng thái PENDING cùng HIT và CACHE trên máy chủ phân giải mô phỏng, tuy nhiên quy trình không thể sinh ra dữ liệu tấn công thực nghiệm do chu trình phân giải cơ sở ban đầu đã bị nghẽn mạch kết nối UDP giữa máy chủ phân giải và máy chủ thẩm quyền, Kết quả đạt FAIL

Tình huống sáu thu thập chuỗi gói tin bắt giữ pcap chứng minh các đợt phát gói đoán mã nhận diện và gói tin phản hồi giả mạo mang bản ghi máy chủ ủy quyền và địa chỉ phụ trợ độc hại, song không thể trích xuất do các gói tin nghiệp vụ đã bị nhân Linux và tường lửa pfSense hủy bỏ ngầm ở tầng giao vận, Kết quả đạt FAIL

Tình huống bảy kiểm tra kết quả phân giải và khả năng điều hướng trang web của máy khách nạn nhân sau đòn tấn công đầu độc, tuy nhiên nạn nhân không bị chuyển hướng sang địa chỉ mười chấm mười chấm không chấm hai trăm của kẻ tấn công và không mở được trang web giả mạo do bộ nhớ đệm của máy chủ phân giải chưa từng tiếp nhận bản ghi ủy quyền giả, Kết quả đạt FAIL

Tình huống tám thực hiện khôi phục máy chủ phân giải BIND chín hiện đại, xóa sạch bộ nhớ đệm bằng lệnh rndc flush, tái kích hoạt dịch vụ named ở trạng thái hoạt động tốt và thiết lập các chính sách gia cố gồm hạn chế quyền đệ quy chỉ dành cho dải mạng tin cậy DNSLAB, áp dụng chế độ phản hồi tối thiểu và tận dụng cơ chế ngẫu nhiên hóa cổng nguồn UDP của BIND chín chấm mười tám, Kết quả đạt PASS

Kết luận tổng thể

Bài thực hành LAB 10 đã làm sáng tỏ sự khác biệt bản chất giữa hình thức tấn công giả mạo DNS cục bộ và tấn công đầu độc bộ nhớ đệm DNS từ xa theo kỹ thuật Kaminsky. Thông qua việc phân chia ranh giới mạng bằng router pfSense và thiết lập chế độ cấm nhận toàn bộ khung truyền trên máy Kali Linux, tính chất tấn công từ xa hoàn toàn mù thông tin về mã định danh giao dịch và cổng nguồn đã được minh chứng một cách thuyết phục. Mặc dù giới hạn phần cứng máy tính buộc phải dồn nhiều vai trò mạng của phân vùng DNSLAB và phân vùng DMZ lên cùng một máy ảo Ubuntu dẫn đến xung đột định tuyến nội bộ và lỗi hết thời gian chờ tại pha đo đạc cơ sở, quá trình xử lý sự cố đã đem lại giá trị học thuật sâu sắc về cơ chế kiểm soát gói tin của nhân mạng Linux, hành vi của tường lửa có trạng thái và giới hạn của switch ảo hóa. Cuối cùng, các biện pháp gia cố máy chủ phân giải như thu hẹp danh sách kiểm soát quyền đệ quy, duy trì xác thực chữ ký điện tử DNSSEC và cơ chế ngẫu nhiên hóa cổng nguồn UDP đã được xác lập hoàn chỉnh, cung cấp giải pháp phòng thủ vững chắc trước các nguy cơ đầu độc bộ nhớ đệm DNS trong thực tế



