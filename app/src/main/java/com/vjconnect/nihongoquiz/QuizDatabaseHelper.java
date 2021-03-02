package com.vjconnect.nihongoquiz;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "quizapp.sqlite";
    static final private int VERSION = 001;

    String[][] quizData = {
            // {"日本語", "アルファベット", "正解", "選択肢１", "選択肢２", "選択肢３", "カテゴリー"}
            // N5-N4
            // 名詞
            {"朝", "asa","Buổi sáng","Bạn bè","Nghỉ","Tình yêu","1"},
            {"家", "ie","Nhà","Điện","Tuần sau","Tình yêu, tình thương","1"},
            {"エアコン", "eakon","Máy điều hòa","Bà","Nghỉ","Ý tưởng","1"},
            {"お母さん", "okasan","Mẹ","Tuần trước","Anh trai","Bắt tay","1"},
            {"外国", "gaikoku","Nước ngoài","Tai nạn, sự cố","Con chó","Truy cập","1"},
            {"季節", "kisetsu","Mùa","Buổi chiều","Sinh viên, học sinh","Trọng âm","1"},
            {"空港", "kuko","Sân bay","Giày","Lớp học","Khắp nơi, đây đó ","1"},
            {"元気", "genki","Khoẻ mạnh","Lớp học","Giày","Thu thập","1"},
            {"高校", "koko","Trường trung học phổ thông","Sinh viên, học sinh","Buổi chiều","Địa chỉ","1"},
            {"魚", "sakana","Cá","Tiền bạc","Tai nạn, sự cố","Dọn dẹp","1"},
            {"時間", "jikan","Thời gian","Ga tàu, nhà ga","Tuần trước","Lời khuyên","1"},
            {"スーパー", "supa","Siêu thị","Con chó","Bà","Hố, hang, lỗ hỏng","1"},
            {"席", "seki","Ghế","Anh trai","Điện","Phần dư, phần còn lại","1"},
            {"外", "soto","Ở ngoài","Bố mẹ","Bạn bè","Bảng câu hỏi","1"},
            {"大学", "daigaku","Trường đại học","Tuần sau","Buổi sáng","Tinh nghịch, nghịch ngợm","1"},
            {"お父さん", "otosan","Bố","Nghỉ","Nhà","Việc nhà, việc nội trợ","1"},
            {"テレビ", "televi","Ti vi","E-mail","Máy điều hòa","Cho vay","1"},
            {"メール", "meru","E-mail","Ti vi","Mẹ","Con số","1"},
            {"休み", "yasumi","Nghỉ","Bố","Nước ngoài","Một hướng, một bên","1"},
            {"来週", "raishu","Tuần sau","Bố","Mùa","Danh mục, Catalog","1"},
            {"両親", "ryosin","Bố mẹ","Trường đại học","Sân bay","Giá trị","1"},
            {"兄", "ani","Anh trai","Ở ngoài","Khoẻ mạnh","Cắt","1"},
            {"犬", "inu","Con chó","Ghế","Trường trung học phổ thông","Hoạt động","1"},
            {"駅", "eki","Ga tàu, nhà ga","Siêu thị","Cá","Nhẫn nhịn, chịu đựng","1"},
            {"お金", "okane","Tiền bạc","Thời gian","Chúa Trời","Tình yêu","1"},
            {"学生", "gakusei","Sinh viên, học sinh","Bầu trời","Ý tưởng","Bắt tay","1"},
            {"教室", "kyoshitsu","Lớp học","Trường trung học phổ thông","Truy cập","Địa chỉ","1"},
            {"靴", "kutsu","Giày","Khoẻ mạnh","Thời gian","Chào mừng, nghênh đón","1"},
            {"午後", "gogo","Buổi chiều","Sân bay","Siêu thị","Du lịch","1"},
            {"事故", "jiko","Tai nạn, sự cố","Mùa","Ghế","Bạn bè","1"},
            {"先週", "sensyu","Tuần trước","Nước ngoài","Ở ngoài","Điện","1"},
            {"祖母", "sobo","Bà","Mẹ","Trường đại học","Tai nạn, sự cố", "1"},
            {"電気", "denki","Điện","Máy điều hòa","Bố","Giày","1"},
            {"友達", "tomodachi","Bạn bè","Nhà","Ti vi","Anh trai","1"},
            // 動詞
            {"会います", "aimasu","Gặp","Tận dụng, phát huy","Sửa chữa","Phù hợp","1"},
            {"行きます", "ikimasu","Đi","Hoảng loạn, bối rối","Dừng lại","Quyết định","1"},
            {"歌います", "utaimasu","Hát","Điều chỉnh, hợp lực","Đi ra ngoài","Hành động","1"},
            {"起きます", "okimasu","Thức dậy","Xuất hiện","Chú ý, cẩn thận","Ngủ trưa","1"},
            {"買います", "kaimasu","Mua","Đan","Gặp","Phát triển","1"},
            {"消えます", "kiemasu","Biến mất, tắt (điện)","Dư thừa","Đi","Điều tra","1"},
            {"答えます", "kotaemasu","Trả lời","Trúng, va chạm","Hát","Tức giận","1"},
            {"閉まります", "simarimasu","Đóng","Gửi, giao phó","Thức dậy","Tiêu thụ","1"},
            {"捨てます", "sutemasu","Vứt, bỏ đi","Trông nom, canh giữ","Mua","La mắng","1"},
            {"洗濯します", "sentakusimasu","Giặt giũ","Nhà","Biến mất, tắt (điện)","Phát biểu","1"},
            {"掃除します", "soujisimasu","Dọn dẹp, quét dọn","Trông nom, canh giữ","Trả lời","Trả lời","1"},
            {"出します", "dasimasu","Đưa ra, lấy ra, gửi đi","Chán, ngấy","Đóng","Sét, bộ","1"},
            {"注意します", "tyuisimasu","Chú ý, cẩn thận","Bỏ cuộc, từ bỏ","Vứt, bỏ đi","Hoạt động","1"},
            {"使います", "tukaimasu","Sử dụng","Yêu, yêu quý","Giặt giũ","Lý giải, hiểu được","1"},
            {"出かけます", "dekakemasu","Đi ra ngoài","Yêu, yêu quý","Dọn dẹp, quét dọn","Nỗ lực","1"},
            {"止まります", "tomarimasu","Dừng lại","Yêu, yêu quý","Chú ý, cẩn thận","Cảm thấy","1"},
            {"直します", "naosimasu","Sửa chữa","Yêu, yêu quý","Sử dụng","Bỏ phiếu","1"},
            // イ形
            {"青い", "aoi", "Màu xanh", "Nhanh", "Nông cạn", "Tuyệt vời, vĩ đại", "1"},
            {"いい", "ii", "Tốt, đẹp, đúng", "Xa", "Kỳ quặc, nực cười", "Kinh khủng, khiếp sợ", "1"},
            {"美しい", "utsukusi", "Xinh đẹp, đẹp đẽ", "Lạnh", "Dịu dàng", "Ngứa", "1"},
            {"おいしい", "oisi", "Ngon", "Nhỏ", "Chặt chẽ, chật chội, hà khắc", "Nghiêm khắc", "1"},
            {"悲しい", "kanasi", "Buồn bã, u sầu", "Cao", "Mùi", "Đáng tiếc", "1"},
            {"汚い", "kitanai", "Dơ bẩn", "Hẹp", "Đau đớn, khổ cực", "Chi tiết, tường tận", "1"},
            {"暗い", "kurai", "Tối", "Ít, một vài", "Đến", "Chi tiết, tỷ mỉ", "1"},
            {"怖い", "kowai", "Sợ hãi", "Trắng", "Thú vị, hấp dẫn", "Sâu", "1"},
            {"寒い", "samui", "Lạnh", "Sợ hãi", "Buồn ngủ", "Ấm áp", "1"},
            {"白い", "siroi", "Trắng", "Lạnh", "Lãng phí", "Chi tiết", "1"},
            {"少ない", "sukunai", "Ít, một vài", "Tối", "Có quy tắc, nề nếp", "Lằng nhằng, dai dẳng", "1"},
            {"狭い", "semai", "Hẹp", "Sợ hãi", "Nghiêm khắc, chặt", "Rất hứng thú", "1"},
            {"高い", "takai", "Cao", "Tối", "Mơ hồ, không rõ ràng", "Phạm vi rộng", "1"},
            {"小さい", "tisai", "Nhỏ", "Dơ bẩn", "Thích hợp", "Nông cạn", "1"},
            {"冷たい", "tumetai", "Lạnh", "Buồn bã, u sầu", "Tuyệt vời, vĩ đại", "Kỳ quặc, nực cười", "1"},
            {"遠い", "toi", "Xa", "Ngon", "福島市", "前橋市", "1"},
            {"速い", "hayai", "Nhanh", "Xinh đẹp, đẹp đẽ", "Kinh khủng, khiếp sợ", "Dịu dàng", "1"},
            // ナ形
            {"いろいろな", "iroirona", "Đa dạng, nhiều", "Quan trọng", "Thật ngạc nhiên", "Chung, nói chung", "1"},
            {"簡単な", "kantanna", "Đơn giản", "Giỏi", "Khác thường", "Điềm đạm, ôn hoà", "1"},
            {"嫌いな", "kiraina", "Ghét", "Nổi tiếng", "Chung, nói chung", "Đảm bảo, chắc chắn", "1"},
            {"元気な", "genkina", "Khỏe mạnh", "Qúa sức, quá khả năng", "Điềm đạm, ôn hoà", "Ích kỷ, tự ý", "1"},
            {"幸せな", "siawasena", "Hạnh phúc", "Chăm chỉ, cần mẫn", "Đảm bảo, chắc chắn", "Có khả năng", "1"},
            {"好きな", "sukina", "Yêu thích", "Tiện lợi", "Ích kỷ, tự ý", "Đáng thương, tội nghiệp", "1"},
            {"大丈夫な", "daijobuna", "Được, ổn, không vấn đề gì", "Bất tiện", "Có khả năng", "Bướng bỉnh, ngoan cố", "1"},
            {"特別な", "tokubetsuna", "Đặc biệt", "Nhộn nhịp", "Đáng thương, tội nghiệp", "Hoàn toàn, toàn diện", "1"},
            {"にぎやかな", "nigiyakana", "Nhộn nhịp", "Đặc biệt", "Bướng bỉnh, ngoan cố", "Mang tính khách quan", "1"},
            {"不便な", "fubenna", "Bất tiện", "Được, ổn, không vấn đề gì", "Hoàn toàn, toàn diện", "Mang tính cụ thể", "1"},
            {"便利な", "benrina", "Tiện lợi", "Yêu thích", "Mang tính khách quan", "Mang tính kinh tế", "1"},
            {"まじめな", "majimena", "Chăm chỉ, cần mẫn", "Hạnh phúc", "Mang tính cụ thể", "Hạnh phúc, vui vẻ", "1"},
            {"無理な", "murina", "Qúa sức, quá khả năng", "Khỏe mạnh", "Mang tính kinh tế", "Chăm chỉ, cần cù", "1"},
            {"有名な", "yumeina", "Nổi tiếng", "Ghét", "Hạnh phúc, vui vẻ", "Khỏe mạnh", "1"},
            {"上手な", "jozuna", "Giỏi", "Đơn giản", "Thật ngạc nhiên", "Phong phú, giàu có", "1"},
            {"大切な", "taisetsuna", "Quan trọng", "Đa dạng, nhiều", "Khác thường", "Có khả năng", "1"},
            // 副詞
            {"いっしょに", "isshoni", "Cùng nhau", "Hầu hết, thường là", "Tạm thời, nhất thời", "Lần đầu tiên", "1"},
            {"必ず", "kanarazu", "Nhất định", "Khoảng chừng", "Cùng một lúc", "Thêm nữa", "1"},
            {"これから", "korekara", "Kể từ bây giờ", "Hoàn toàn", "Một ngày nào đó", "Trực tiếp", "1"},
            {"最近", "saikin", "Gần đây", "Bất cứ lúc nào", "Lúc nào không biết", "Không ...lắm", "1"},
            {"たくさん", "takusan", "Nhiều", "Không...lắm", "Mãi mãi", "Nhiều, đầy ắp", "1"},
            {"どうやって", "douyatte", "Bằng cách nào", "Một chút nữa", "Bất cứ lúc nào", "Một lát sau", "1"},
            {"いつも", "itsumo", "Luôn luôn", "Rất, cực kỳ", "Cuối cùng thì", "Như thế này", "1"},
            {"今度", "kondo", "Lần này, lần tới", "Đôi khi, thỉnh thoảng", "Vô ý, lơ đãng", "Nhất , dù thế nào vẫn", "1"},
            {"時々", "tokidoki", "Đôi khi, thỉnh thoảng", "Lần này, lần tới", "Có lẽ", "Để nguyên, như vốn dĩ", "1"},
            {"とても", "totemo", "Rất, cực kỳ", "Luôn luôn", "Cùng", "Ví dụ", "1"},
            {"もう少し", "mosukoshi", "Một chút nữa", "Bằng cách nào", "Theo tỷ lệ", "Chưa hẳn đã ", "1"},
            {"あまり", "amari", "Không...lắm", "Nhiều", "Ngược lại", "Dần dần", "1"},
            {"いつでも", "itsudemo", "Bất cứ lúc nào", "Gần đây", "Khá là, tương đối ", "Ngay lập tức", "1"},
            {"全然", "zenzen", "Hoàn toàn", "Kể từ bây giờ", "Gọn gàng, chỉn chu", "Từ đầu", "1"},
            {"だいたい", "daitai", "Khoảng chừng", "Nhất định", "Vô cùng, cực kỳ", "Đột ngột", "1"},
            {"たいてい", "taitei", "Hầu hết, thường là", "Cùng nhau", "Tóm lại là, tức là", "Dám", "1"},

            // N3
            // 名詞
            {"愛", "ai", "Tình yêu", "Buổi sáng", "Tuần trước", "Cao học", "2"},
            {"愛情", "aijo", "Tình yêu, tình thương", "Nhà", "Bà", "Mẫu giáo", "2"},
            {"アイデア", "aideaa", "Ý tưởng", "Máy điều hòa", "Điện", "Phần", "2"},
            {"握手", "akushu", "Bắt tay", "Mẹ", "Bạn bè", "Khu vực", "2"},
            {"アクセス", "akusesu", "Truy cập", "Nước ngoài", "Đối phương, đối tượng", "Sáng nay", "2"},
            {"アクセント", "akusento", "Trọng âm", "Mùa", "Lý do, nguyên nhân", "Cá tính", "2"},
            {"あちらこちら", "achirakochira", "Khắp nơi, đây đó ", "Sân bay", "Ký giả, phóng viên", "Thành quả", "2"},
            {"集まり", "atsumari", "Thu thập", "Khoẻ mạnh", "Phương pháp", "Thế hệ", "2"},
            {"宛名", "atena", "Địa chỉ", "Cá", "Nguyên nhân", "Khoa, ngành học", "2"},
            {"後片付け", "atokatazuke", "Dọn dẹp", "Thời gian", "Nội dung", "Vi sinh vật", "2"},
            {"アドバイス", "adobaisu", "Lời khuyên", "Siêu thị", "Thích hợp", "Hàng năm", "2"},
            {"穴", "ana", "Hố, hang, lỗ hỏng", "Ghế", "Giấc ngủ", "Cấp dưới", "2"},
            {"余り", "amari", "Phần dư, phần còn lại", "Ở ngoài", "Nhóm", "Thị trường", "2"},
            {"アンケート", "anketo", "Bảng câu hỏi", "Trường đại học", "Tài liệu", "Mùi hôi", "2"},
            {"いたずら", "itazura", "Tinh nghịch, nghịch ngợm", "Bố", "Tiêu dùng", "Chuyện kinh dị", "2"},
            {"家事", "kaji", "Việc nhà, việc nội trợ", "Doanh nghiệp", "Ti vi", "Giới trẻ", "2"},
            {"貸し出し", "kashidashi", "Cho vay", "E-mail", "Kết quả", "Giá trị số", "2"},
            {"数", "kazu", "Con số", "Nghỉ", "Nghề nghiệp", "Nhà máy", "2"},
            {"片方", "kataho", "Một hướng, một bên", "Tuần sau", "Nhân viên", "Cơ cấu, tổ chức", "2"},
            {"カタログ", "katarogu", "Danh mục, Catalog", "Bố mẹ", "Toàn bộ", "Sổ tay hướng dẫn", "2"},
            {"価値", "kachi", "Giá trị", "Anh trai", "Lợi nhuận", "Cửa hàng", "2"},
            {"カット", "katto", "Cắt", "Con chó", "Thế giới", "Tiếng ồn", "2"},
            {"活動", "katsudo", "Hoạt động", "Ga tàu, nhà ga", "Nước mắt", "Điển hình", "2"},
            {"我慢", "gaman", "Nhẫn nhịn, chịu đựng", "Tiền bạc", "Mọi người", "Khí CO2", "2"},
            {"神", "kami", "Chúa Trời", "Sinh viên, học sinh", "Chính phủ ", "Bảng câu hỏi", "2"},
            {"空", "sora", "Bầu trời", "Lớp học", "Phút", "Hình thái", "2"},
            {"革", "kawa", "Da", "Giày", "Câu hỏi", "Hiệu suất, năng suất", "2"},
            {"歓迎", "kangei", "Chào mừng, nghênh đón", "Buổi chiều", "Sự vật, sự việc", "Thị giác", "2"},
            {"観光", "kanko", "Du lịch", "Tai nạn, sự cố", "Giữa chừng", "Tiền vốn", "2"},
            // 動詞
            {"愛する", "aisuru", "Yêu, yêu quý", "Gặp", "Đi ra ngoài", "Hoạt động", "2"},
            {"諦める", "akirameru", "Bỏ cuộc, từ bỏ", "Đi", "Dừng lại", "Lý giải, hiểu được", "2"},
            {"飽きる", "akiru", "Chán, ngấy", "Hát", "Sửa chữa", "Nỗ lực", "2"},
            {"預かる", "azukaru", "Trông nom, canh giữ", "Thức dậy", "Phù hợp", "Cảm thấy", "2"},
            {"預ける", "azukeru", "Gửi, giao phó", "Mua", "Quyết định", "Thiết kế", "2"},
            {"暖める", "atatameru", "Làm nóng lên (nhiệt độ không khí)", "Biến mất, tắt (điện)", "Hành động", "Chức năng", "2"},
            {"温める", "atatameru", "Làm nóng, hâm nóng ( cơ thể, đồ ăn)", "Trả lời", "Ngủ trưa", "Nắm bắt", "2"},
            {"当たる", "ataru", "Trúng, va chạm", "Đóng", "Điều tra", "Bỏ phiếu", "2"},
            {"余る", "amaru", "Dư thừa", "Vứt, bỏ đi", "Tức giận", "Ghi danh, đăng ký", "2"},
            {"編む", "amu", "Đan", "Giặt giũ", "Tiêu thụ", "Bố trí, sắp đặt", "2"},
            {"現れる", "arawareru", "Xuất hiện", "Dọn dẹp, quét dọn", "La mắng", "Mở rộng", "2"},
            {"合わせる", "awaseru", "Điều chỉnh, hợp lực", "Đưa ra, lấy ra, gửi đi", "Phát biểu", "Phát sinh", "2"},
            {"慌てる", "awateru", "Hoảng loạn, bối rối", "Chú ý, cẩn thận", "Trả lời", "Tương đương", "2"},
            {"生かす", "ikasu", "Tận dụng, phát huy", "Sử dụng", "Sét, bộ", "Đạt được", "2"},
            // イ形
            {"浅い", "asai", "Nông cạn", "Màu xanh", "Lạnh", "Phạm vi rộng", "2"},
            {"偉い", "erai", "Tuyệt vời, vĩ đại", "Tốt, đẹp, đúng", "Xa", "Thích hợp", "2"},
            {"おかしい", "okashi", "Kỳ quặc, nực cười", "Xinh đẹp, đẹp đẽ", "Nhanh", "Nhanh", "2"},
            {"恐ろしい", "osoroshi", "Kinh khủng, khiếp sợ", "Ngon", "Thú vị, hấp dẫn", "Xa", "2"},
            {"おとなしい", "otonashi", "Dịu dàng", "Buồn bã, u sầu", "Sâu", "Lạnh", "2"},
            {"かゆい", "kayui", "Ngứa", "Dơ bẩn", "Buồn ngủ", "Nhỏ", "2"},
            {"きつい", "kitsui", "Chặt chẽ, chật chội, hà khắc", "Tối", "Ấm áp", "Cao", "2"},
            {"厳しい", "kibishi", "Nghiêm khắc", "Sợ hãi", "Lãng phí", "Hẹp", "2"},
            {"臭い", "kusai", "Mùi", "Lạnh", "Chi tiết", "Ít, một vài", "2"},
            {"悔しい", "kuyashi", "Đáng tiếc", "Trắng", "Có quy tắc, nề nếp", "Trắng", "2"},
            {"苦しい", "kurushi", "Đau đớn, khổ cực", "Ít, một vài", "Lằng nhằng, dai dẳng", "Lạnh", "2"},
            {"詳しい", "kuwashi", "Chi tiết, tường tận", "Hẹp", "Nghiêm khắc, chặt", "Sợ hãi", "2"},
            {"来い", "koi", "Đến", "Cao", "Rất hứng thú", "前橋市", "2"},
            {"細かい", "komakai", "Chi tiết, tỷ mỉ", "Nhỏ", "Mơ hồ, không rõ ràng", "Tối", "2"},
            // ナ形
            {"意外な", "igaina", "Thật ngạc nhiên", "Đa dạng, nhiều", "Nổi tiếng", "Khá, tương đối", "2"},
            {"異常な", "ijona", "Khác thường", "Đơn giản", "Giỏi", "Cực kỳ, rất laf", "2"},
            {"一般的な", "ippantekina", "Chung, nói chung", "Ghét", "Quan trọng", "Thích hợp", "2"},
            {"穏やかな", "odayakana", "Điềm đạm, ôn hoà", "Khỏe mạnh", "Chăm chỉ, cần cù", "Trung thành", "2"},
            {"確実な", "kakujitsuna", "Đảm bảo, chắc chắn", "Hạnh phúc", "Khỏe mạnh", "Lộng lẫy, rực rỡ", "2"},
            {"勝手な", "kattena", "Ích kỷ, tự ý", "Phong phú, giàu có", "Yêu thích", "Chưa thuần thục", "2"},
            {"可能な", "kanona", "Có khả năng", "Được, ổn, không vấn đề gì", "Có khả năng", "Đương nhiên", "2"},
            {"かわいそうな", "kawaisona", "Đáng thương, tội nghiệp", "Lo lắng, bất an", "Quan trọng", "Lớn, rộng, nhiều", "2"},
            {"頑固な", "gankona", "Bướng bỉnh, ngoan cố", "Đặc biệt", "Mang tính cụ thể", "Chất lượng tốt", "2"},
            {"完全な", "kanzenna", "Hoàn toàn, toàn diện", "Nhộn nhịp", "Yếu, kém", "Không tinh khiết", "2"},
            {"客観的な", "kyakkantekina", "Mang tính khách quan", "Bất tiện", "Xác thực, chính xác", "Cẩu thả, khinh suất", "2"},
            {"具体的な", "gutaitekina", "Mang tính cụ thể", "Tiện lợi", "Hoàn toàn, toàn diện", "Độc nhất, độc đáo", "2"},
            {"経済的な", "keizaitekina", "Mang tính kinh tế", "Chăm chỉ, cần mẫn", "Mang tính tiêu cực", "Thẳng thắn, trực tính", "2"},
            {"幸福な", "kofukuna", "Hạnh phúc, vui vẻ", "Qúa sức, quá khả năng", "Tự nhiên", "Cao quý, tao nhã", "2"},
            // 副詞
            {"一時", "ichiji", "Tạm thời, nhất thời", "Cùng nhau", "Khoảng chừng", "Chưa hẳn đã ", "2"},
            {"一度に", "ichidoni", "Cùng một lúc", "Nhất định", "Hầu hết, thường là", "Dần dần", "2"},
            {"いつか", "itsuka", "Một ngày nào đó", "Kể từ bây giờ", "Vô cùng, cực kỳ", "Gọn gàng, ngay ngắn", "2"},
            {"いつの間にか", "itsunomanika", "Lúc nào không biết", "Gần đây", "Tóm lại là, tức là", "Ngay lập tức", "2"},
            {"いつまでも", "itsumademo", "Mãi mãi", "Nhiều", "Lần đầu tiên", "Đột ngột", "2"},
            {"今にも", "imanimo", "Bất cứ lúc nào", "Bằng cách nào", "Thêm nữa", "Một loạt, một dãy", "2"},
            {"いよいよ", "iyoiyo", "Cuối cùng thì", "Luôn luôn", "Trực tiếp", "Quả nhiên, đúng như dự đoán", "2"},
            {"うっかり", "ukkari", "Vô ý, lơ đãng", "Lần này, lần tới", "Không ...lắm", "Từ đầu", "2"},
            {"恐らく", "osoraku", "Có lẽ", "Đôi khi, thỉnh thoảng", "Nhiều, đầy ắp", "Không quan tâm, bất chấp", "2"},
            {"お互いに", "otagaini", "Cùng", "Rất, cực kỳ", "Một lát sau", "Cất công làm cho", "2"},
            {"割合に", "wariaini", "Theo tỷ lệ", "Một chút nữa", "Như thế này", "Biết bao, như thế nào", "2"},
            {"かえって", "kaette", "Ngược lại", "Không...lắm", "Nhất , dù thế nào vẫn", "Trước kia, đã từng", "2"},
            {"かなり", "kanari", "Khá là, tương đối ", "Bất cứ lúc nào", "Để nguyên, như vốn dĩ", "Hơn bất cứ thứ gì", "2"},
            {"きちんと", "kichinto", "Gọn gàng, chỉn chu", "Hoàn toàn", "Ví dụ", "Ngu ngốc, dại dột", "2"},
            // その他
            {"一方", "ippo", "Mặt khác", "Mang tính〜", "Đột ngột", "Ngu ngốc, dại dột", "2"},
            {"さて", "sate", "Và bây giờ, và sau đây", "Gì, cái gì?", "Một loạt, một dãy", "Hơn bất cứ thứ gì", "2"},
            {"すると", "suruto", "Và sau đó", "Bởi, vì 〜", "Quả nhiên, đúng như dự đoán", "Trước kia, đã từng", "2"},
            {"そう言えば", "souieba", "Nếu nói như vậy thì", "Đối với〜", "Từ đầu", "Biết bao, như thế nào", "2"},
            {"そこで", "sokode", "Vì thế, do đó", " Khoảng〜", "Không quan tâm, bất chấp", "Cất công làm cho", "2"},
            {"そのうえ", "sonoue", "Ngoài ra", "Tiền〜", "Cất công làm cho", "Không quan tâm, bất chấp", "2"},
            {"そのため", "sonotame", "Vì lý do đó", "Tiền〜", "Biết bao, như thế nào", "Từ đầu", "2"},
            {"それで", "sorede", "Do đó, bởi vậy", "Vị thứ〜", "Trước kia, đã từng", "Quả nhiên, đúng như dự đoán", "2"},
            {"それとも", "soretomo", "Hoặc là", "Hoặc là", "Hơn bất cứ thứ gì", "Một loạt, một dãy", "2"},
            {"それなら", "sorenara", "Nếu như thế", "Cùng với", "Ngu ngốc, dại dột", "Đột ngột", "2"},
            {"それに", "soreni", "Ngoài ra", "〜Phụ thuộc vào", "Tương đối, khá là", "Ngay lập tức", "2"},
            {"だが", "daga", "Nhưng", "Gọn gàng, ngay ngắn", "Gọn gàng, ngay ngắn", "Gì, cái gì?", "2"},
            {"つまり", "tsumari", "Nói cách khác", "Dám", "Bởi, vì〜", "Mang tính〜", "2"},
            {"または", "mataha", "Hoặc là", "Ngay lập tức", "Tương đối, khá là", "Đối với〜", "2"},

            // N2
            // 名詞
            {"相手", "aite", "Đối phương, đối tượng", "Buổi sáng", "Tuần trước", "Da", "3"},
            {"わけ", "wake", "Lý do, nguyên nhân", "Nhà", "Bà", "Chào mừng, nghênh đón", "3"},
            {"筆者", "hissha", "Ký giả, phóng viên", "Máy điều hòa", "Điện", "Du lịch", "3"},
            {"方法", "houho", "Phương pháp", "Mẹ", "Bạn bè", "Cá tính", "3"},
            {"理由", "riyu", "Nguyên nhân", "Nước ngoài", "Tình yêu", "Thành quả", "3"},
            {"内容", "naiyo", "Nội dung", "Mùa", "Tình yêu, tình thương", "Thế hệ", "3"},
            {"適当", "tekitou", "Thích hợp", "Sân bay", "Ý tưởng", "Khoa, ngành học", "3"},
            {"睡眠", "suimin", "Giấc ngủ", "Khoẻ mạnh", "Bắt tay", "Vi sinh vật", "3"},
            {"グループ", "gurupu", "Nhóm", "Trường trung học phổ thông", "Truy cập", "Hàng năm", "3"},
            {"書類", "shorui", "Tài liệu", "Cá", "Trọng âm", "Cấp dưới", "3"},
            {"消費", "shohi", "Tiêu dùng", "Thời gian", "Khắp nơi, đây đó ", "Thị trường", "3"},
            {"企業", "kigyo", "Doanh nghiệp", "Siêu thị", "Thu thập", "Mùi hôi", "3"},
            {"結果", "kekka", "Kết quả", "Ghế", "Địa chỉ", "Chuyện kinh dị", "3"},
            {"職業", "shokugyo", "Nghề nghiệp", "Ở ngoài", "Dọn dẹp", "Giới trẻ", "3"},
            {"社員", "shain", "Nhân viên", "Trường đại học", "Lời khuyên", "Giá trị số", "3"},
            {"全体", "zentai", "Toàn bộ", "Bố", "Hố, hang, lỗ hỏng", "Nhà máy", "3"},
            {"利益", "rieki", "Lợi nhuận", "Ti vi", "Phần dư, phần còn lại", "Cơ cấu, tổ chức", "3"},
            {"世界", "sekai", "Thế giới", "E-mail", "Bảng câu hỏi", "Sổ tay hướng dẫn", "3"},
            {"涙", "namida", "Nước mắt", "Nghỉ", "Tinh nghịch, nghịch ngợm", "Cửa hàng", "3"},
            {"人々", "hitobito", "Mọi người", "Tuần sau", "Việc nhà, việc nội trợ", "Tiếng ồn", "3"},
            {"政府", "seihu", "Chính phủ ", "Bố mẹ", "Cho vay", "Điển hình", "3"},
            {"分", "hun", "Phút", "Anh trai", "Con số", "Khí CO2", "3"},
            {"疑問", "gimon", "Câu hỏi", "Con chó", "Một hướng, một bên", "Bảng câu hỏi", "3"},
            {"物事", "monogoto", "Sự vật, sự việc", "Ga tàu, nhà ga", "Danh mục, Catalog", "Hình thái", "3"},
            {"途中", "totyu", "Giữa chừng", "Tiền bạc", "Giá trị", "Hiệu suất, năng suất", "3"},
            {"大学院", "daigakuin", "Cao học", "Sinh viên, học sinh", "Cắt", "Thị giác", "3"},
            {"幼稚園", "yochien", "Mẫu giáo", "Lớp học", "Hoạt động", "Tiền vốn", "3"},
            {"部分", "bubun", "Phần", "Giày", "Nhẫn nhịn, chịu đựng", "Nước nóng", "3"},
            {"地域", "tiki", "Khu vực", "Buổi chiều", "Chúa Trời", "Thuốc độc, độc hại", "3"},
            {"今朝", "kesa", "Sáng nay", "Tai nạn, sự cố", "Bầu trời", "Lý tính, lý trí", "3"},
            // 動詞
            {"合う", "au", "Phù hợp", "Gặp", "Dừng lại", "Tận dụng, phát huy", "3"},
            {"決める", "kimeru", "Quyết định", "Đ i", "Sửa chữa", "Phát triển, khai thác", "3"},
            {"行動する", "kodosuru", "Hành động", "Hát", "Yêu, yêu quý", "Dự tưởng, tiên đoán", "3"},
            {"昼寝する", "hirunesuru", "Ngủ trưa", "Thức dậy", "Bỏ cuộc, từ bỏ", "Thiết kế", "3"},
            {"調査する", "tyosasuru", "Điều tra", "Mua", "Chán, ngấy", "Nắm bắt", "3"},
            {"怒る", "okoru", "Tức giận", "Trông nom, canh giữ", "Biến mất, tắt (điện)", "Chức năng", "3"},
            {"消費する", "shohisuru", "Tiêu thụ", "Trả lời", "Gửi, giao phó", "Nắm bắt", "3"},
            {"叱る", "shikaru", "La mắng", "Đóng", "Làm nóng lên (nhiệt độ không khí)", "Bỏ phiếu", "3"},
            {"発表する", "happyosuru", "Phát biểu", "Vứt, bỏ đi", "Làm nóng, hâm nóng ( cơ thể, đồ ăn)", "Ghi danh, đăng ký", "3"},
            {"回答する", "kaitosuru", "Trả lời", "Giặt giũ", "Trúng, va chạm", "Bố trí, sắp đặt", "3"},
            {"セットする", "settosuru", "Sét, bộ", "Dọn dẹp, quét dọn", "Dư thừa", "Mở rộng", "3"},
            {"活動する", "katsudosuru", "Hoạt động", "Đưa ra, lấy ra, gửi đi", "Đan", "Phát sinh", "3"},
            {"理解する", "rikaisuru", "Lý giải, hiểu được", "Chú ý, cẩn thận", "Xuất hiện", "Nhìn nhận, nhận thức", "3"},
            {"努力する", "doryokusuru", "Nỗ lực", "Sử dụng", "Điều chỉnh, hợp lực", "Tương đương", "3"},
            {"感じる", "kanjiru", "Cảm thấy", "Đi ra ngoài", "Hoảng loạn, bối rối", "Đạt được", "3"},
            // イ形
            {"面白い", "omoshiroi", "Thú vị, hấp dẫn", "Màu xanh", "Trắng", "Tuyệt vời, vĩ đại", "3"},
            {"深い", "fukai", "Sâu", "Tốt, đẹp, đúng", "Ít, một vài", "Kỳ quặc, nực cười", "3"},
            {"眠い", "nemui", "Buồn ngủ", "Xinh đẹp, đẹp đẽ", "Hẹp", "Kinh khủng, khiếp sợ", "3"},
            {"暖かい", "atatakai", "Ấm áp", "Ngon", "Cao", "Dịu dàng", "3"},
            {"もったいない", "mottainai", "Lãng phí", "Buồn bã, u sầu", "Nhỏ", "Ngứa", "3"},
            {"細かい", "komakai", "Chi tiết", "Dơ bẩn", "Lạnh", "Chặt chẽ, chật chội, hà khắc", "3"},
            {"規則正しい", "kisokutadashi", "Có quy tắc, nề nếp", "Tối", "Xa", "Nghiêm khắc", "3"},
            {"しつこい", "shitukoi", "Lằng nhằng, dai dẳng", "Sợ hãi", "Nhanh", "Mùi", "3"},
            {"きつい", "kitsui", "Nghiêm khắc, chặt", "Lạnh", "Nông cạn", "Đáng tiếc", "3"},
            // ナ形
            {"一生懸命な", "isshokemmeina", "Chăm chỉ, cần cù", "Đa dạng, nhiều", "Quan trọng", "Trung thành", "3"},
            {"健康な", "kenkona", "Khỏe mạnh", "Đơn giản", "Thật ngạc nhiên", "Lộng lẫy, rực rỡ", "3"},
            {"豊かな", "yutakana", "Phong phú, giàu có", "Ghét", "Khác thường", "Chưa thuần thục", "3"},
            {"可能な", "kanona", "Có khả năng", "Khỏe mạnh", "Chung, nói chung", "Đương nhiên", "3"},
            {"不安な", "fuanna", "Lo lắng, bất an", "Hạnh phúc", "Điềm đạm, ôn hoà", "Lớn, rộng, nhiều", "3"},
            {"重要な", "jyuyona", "Quan trọng", "Yêu thích", "Đảm bảo, chắc chắn", "Chất lượng tốt", "3"},
            {"具体的な", "gutaitekina", "Mang tính cụ thể", "Được, ổn, không vấn đề gì", "Ích kỷ, tự ý", "Không tinh khiết", "3"},
            {"苦手な", "nigatena", "Yếu, kém", "Đặc biệt", "Có khả năng", "Cẩu thả, khinh suất", "3"},
            {"確か", "tashika", "Xác thực, chính xác", "Nhộn nhịp", "Đáng thương, tội nghiệp", "Độc nhất, độc đáo", "3"},
            {"完全な", "kanzenna", "Hoàn toàn, toàn diện", "Bất tiện", "Bướng bỉnh, ngoan cố", "Thẳng thắn, trực tính", "3"},
            {"消極的な", "shokyokutekina", "Mang tính tiêu cực", "Tiện lợi", "Hoàn toàn, toàn diện", "Cao quý, tao nhã", "3"},
            {"自然な", "sizenna", "Tự nhiên", "Chăm chỉ, cần mẫn", "Mang tính khách quan", "U sầu, chán nán", "3"},
            {"かなりな", "kanarina", "Khá, tương đối", "Qúa sức, quá khả năng", "Mang tính cụ thể", "Vui vẻ, sáng sủa", "3"},
            {"非常に", "hijoni", "Cực kỳ, rất laf", "Nổi tiếng", "Mang tính kinh tế", "Nhạy cảm", "3"},
            {"適切な", "tekisetsuna", "Thích hợp", "Giỏi", "Hạnh phúc, vui vẻ", "Nhanh chóng", "3"},
            // 副詞
            {"最も", "mottomo", "Vô cùng, cực kỳ", "Cùng nhau", "Khoảng chừng", "Hầu hết, thường là", "3"},
            {"つまり", "tsumari", "Tóm lại là, tức là", "Nhất định", "Tạm thời, nhất thời", "Cùng một lúc", "3"},
            {"初めて", "hajimete", "Lần đầu tiên", "Kể từ bây giờ", "Một ngày nào đó", "Gọn gàng, ngay ngắn", "3"},
            {"さらに", "sarani", "Thêm nữa", "Gần đây", "Lúc nào không biết", "Ngay lập tức", "3"},
            {"直接", "tyokusetsu", "Trực tiếp", "Nhiều", "Mãi mãi", "Đột ngột", "3"},
            {"あまり", "amari", "Không ...lắm", "Bằng cách nào", "Bất cứ lúc nào", "Một loạt, một dãy", "3"},
            {"たっぷり", "tappuri", "Nhiều, đầy ắp", "Luôn luôn", "Cuối cùng thì", "Quả nhiên, đúng như dự đoán", "3"},
            {"のちほど", "nochihodo", "Một lát sau", "Lần này, lần tới", "Vô ý, lơ đãng", "Từ đầu", "3"},
            {"こんなに", "konnani", "Như thế này", "Đôi khi, thỉnh thoảng", "Có lẽ", "Không quan tâm, bất chấp", "3"},
            {"どうしても", "doushitemo", "Nhất , dù thế nào vẫn", "Rất, cực kỳ", "Cùng", "Cất công làm cho", "3"},
            {"そのまま", "sonomama", "Để nguyên, như vốn dĩ", "Một chút nữa", "Theo tỷ lệ", "Biết bao, như thế nào", "3"},
            {"例え", "tatoe", "Ví dụ", "Không...lắm", "Ngược lại", "Hơn bất cứ thứ gì", "3"},
            {"必ずしも", "kanarazushimo", "Chưa hẳn đã ", "Bất cứ lúc nào", "Khá là, tương đối ", "Trước kia, đã từng", "3"},
            {"だんだん", "dandan", "Dần dần", "Hoàn toàn", "Gọn gàng, chỉn chu", "Ngu ngốc, dại dột", "3"},

            // N1
            // 名詞
            {"個性", "kosei", "Cá tính", "Buổi sáng", "Điện", "Lý do, nguyên nhân", "4"},
            {"成果", "seika", "Thành quả", "Nhà", "Bạn bè", "Ký giả, phóng viên", "4"},
            {"世代", "sedai", "Thế hệ", "Máy điều hòa", "Tình yêu", "Phương pháp", "4"},
            {"学部", "gakubu", "Khoa, ngành học", "Mẹ", "Tình yêu, tình thương", "Nguyên nhân", "4"},
            {"微生物", "biseibutsu", "Vi sinh vật", "Nước ngoài", "Ý tưởng", "Nội dung", "4"},
            {"年次", "nenji", "Hàng năm", "Mùa", "Bắt tay", "Thích hợp", "4"},
            {"部下", "buka", "Cấp dưới", "Sân bay", "Truy cập", "Giấc ngủ", "4"},
            {"市場", "sijo", "Thị trường", "Khoẻ mạnh", "Trọng âm", "Nhóm", "4"},
            {"悪臭", "akushu", "Mùi hôi", "Trường trung học phổ thông", "Khắp nơi, đây đó ", "Tài liệu", "4"},
            {"怪談", "kaidan", "Chuyện kinh dị", "Cá", "Thu thập", "Tiêu dùng", "4"},
            {"若者", "wakamono", "Giới trẻ", "Thời gian", "Địa chỉ", "Doanh nghiệp", "4"},
            {"数値", "suchi", "Giá trị số", "Siêu thị", "Dọn dẹp", "Kết quả", "4"},
            {"工場", "kojo", "Nhà máy", "Ghế", "Lời khuyên", "Nghề nghiệp", "4"},
            {"仕組み", "shikumi", "Cơ cấu, tổ chức", "Ở ngoài", "Hố, hang, lỗ hỏng", "Nhân viên", "4"},
            {"マニュアル", "manyuaru", "Sổ tay hướng dẫn", "Trường đại học", "Phần dư, phần còn lại", "Toàn bộ", "4"},
           {"商店", "shoten", "Cửa hàng", "Bố", "Bảng câu hỏi", "Lợi nhuận", "4"},
           {"騒音", "souon", "Tiếng ồn", "Ti vi", "Tinh nghịch, nghịch ngợm", "Thế giới", "4"},
           {"典型", "tenkei", "Điển hình", "E-mail", "Việc nhà, việc nội trợ", "Nước mắt", "4"},
           {"二酸化炭素", "nisankatanso", "Khí CO2", "Nghỉ", "Cho vay", "Mọi người", "4"},
           {"アンケート", "anketo", "Bảng câu hỏi", "Tuần sau", "Con số", "Chính phủ", "4"},
           {"形態", "keitai", "Hình thái", "Bố mẹ", "Một hướng, một bên", "Phút", "4"},
           {"効率", "koritdu", "Hiệu suất, năng suất", "Anh trai", "Danh mục, Catalog", "Câu hỏi", "4"},
           {"視覚", "shikaku", "Thị giác", "Con chó", "Giá trị", "Sự vật, sự việc", "4"},
           {"資本", "shihon", "Tiền vốn", "Ga tàu, nhà ga", "Cắt", "Giữa chừng", "4"},
           {"湯水", "yumizu", "Nước nóng", "Tiền bạc", "Hoạt động", "Cao học", "4"},
           {"毒", "doku", "Thuốc độc, độc hại", "Sinh viên, học sinh", "Nhẫn nhịn, chịu đựng", "Mẫu giáo", "4"},
           {"理性", "risei", "Lý tính, lý trí", "Lớp học", "Chúa Trời", "Phần", "4"},
           {"お勧め", "osusume", "Đề xuất, gợi ý", "Giày", "Bầu trời", "Khu vực", "4"},
           {"隊員", "taiin", "Thành viên", "Buổi chiều", "Da", "Sáng nay", "4"},
           {"証拠", "shoko", "Chứng cớ", "Tai nạn, sự cố", "Chào mừng, nghênh đón", "Cá tính", "4"},
           {"葉書", "hagaki", "Bưu thiếp", "Tuần trước", "Du lịch", "Thành quả", "4"},
           {"メーカー", "meka", "Nhà sản xuất", "Bà", "Đối phương, đối tượng", "Thế hệ", "4"},
           // 動詞
           {"開発する", "kaihatsusuru", "Phát triển, khai thác", "Gặp", "Dừng lại", "Tận dụng, phát huy", "4"},
           {"予想する", "yososuru", "Dự tưởng, tiên đoán", "Đi", "Sửa chữa", "Phù hợp", "4"},
           {"デザインする", "dezainsuru", "Thiết kế", "Hát", "Yêu, yêu quý", "Quyết định", "4"},
           {"機能する", "kinosuru", "Chức năng", "Thức dậy", "Bỏ cuộc, từ bỏ", "Hành động", "4"},
           {"把握する", "haakusuru", "Nắm bắt", "Mua", "Chán, ngấy", "Ngủ trưa", "4"},
           {"投票する", "tohyosuru", "Bỏ phiếu", "Biến mất, tắt (điện)", "Trông nom, canh giữ", "Điều tra", "4"},
           {"在籍する", "zaisekisuru", "Ghi danh, đăng ký", "Trả lời", "Gửi, giao phó", "Tức giận", "4"},
           {"配置する", "haichisuru", "Bố trí, sắp đặt", "Đóng", "Làm nóng lên (nhiệt độ không khí)", "Tiêu thụ", "4"},
           {"拡大する", "kakudaisuru", "Mở rộng", "Vứt, bỏ đi", "Làm nóng, hâm nóng ( cơ thể, đồ ăn)", "La mắng", "4"},
           {"発生する", "hasseisuru", "Phát sinh", "Giặt giũ", "Trúng, va chạm", "Phát biểu", "4"},
           {"認識する", "ninshikisuru", "Nhìn nhận, nhận thức", "Dọn dẹp, quét dọn", "Dư thừa", "Phát biểu", "4"},
           {"相当する", "sotosuru", "Tương đương", "Đưa ra, lấy ra, gửi đi", "Đan", "Trả lời", "4"},
           {"達成する", "tasseisuru", "Đạt được", "Chú ý, cẩn thận", "Xuất hiện", "Sét, bộ", "4"},
           {"体験する", "taikensuru", "Kinh nghiệm", "Sử dụng", "Điều chỉnh, hợp lực", "Hoạt động", "4"},
           {"発展する", "hattensuru", "Phát triển", "Đi ra ngoài", "Hoảng loạn, bối rối", "Lý giải, hiểu được", "4"},
           // イ形
           {"興味深い", "kyomibukai", "Rất hứng thú", "Sâu", "Nông cạn", "Dịu dàng", "4"},
           {"紛らわしい", "magirawashi", "Mơ hồ, không rõ ràng", "Chi tiết", "Tuyệt vời, vĩ đại", "Nghiêm khắc", "4"},
           {"幅広い", "habahiroi", "Phạm vi rộng", "Ấm áp", "Kỳ quặc, nực cười", "Đáng tiếc", "4"},
           {"ふさわしい", "fusawashi", "Thích hợp", "Có quy tắc, nề nếp", "Kinh khủng, khiếp sợ", "Đau đớn, khổ cực", "4"},
           // ナ形
           {"忠実な", "chujitsuna", "Trung thành", "Đa dạng, nhiều", "Thật ngạc nhiên", "Phong phú, giàu có", "4"},
           {"華やかな", "hanayakana", "Lộng lẫy, rực rỡ", "Đơn giản", "Khác thường", "Có khả năng", "4"},
           {"未熟な", "mijukuna", "Chưa thuần thục", "Ghét", "Chung, nói chung", "Lo lắng, bất an", "4"},
           {"当然な", "tozenna", "Đương nhiên", "Khỏe mạnh", "Điềm đạm, ôn hoà", "Quan trọng", "4"},
           {"大幅な", "ohabana", "Lớn, rộng, nhiều", "Hạnh phúc", "Đảm bảo, chắc chắn", "Mang tính cụ thể", "4"},
           {"良質な", "ryoshitsuna", "Chất lượng tốt", "Yêu thích", "Ích kỷ, tự ý", "Yếu, kém", "4"},
           {"不純な", "hujunna", "Không tinh khiết", "Được, ổn, không vấn đề gì", "Có khả năng", "Xác thực, chính xác", "4"},
           {"軽率な", "keisotsuna", "Cẩu thả, khinh suất", "Đặc biệt", "Đáng thương, tội nghiệp", "Hoàn toàn, toàn diện", "4"},
           {"独自な", "dokujina", "Độc nhất, độc đáo", "Nhộn nhịp", "Bướng bỉnh, ngoan cố", "Mang tính tiêu cực", "4"},
           {"露骨な", "rokotsuna", "Thẳng thắn, trực tính", "Bất tiện", "Hoàn toàn, toàn diện", "Tự nhiên", "4"},
           {"高尚な", "koshona", "Cao quý, tao nhã", "Tiện lợi", "Mang tính khách quan", "Khá, tương đối", "4"},
           {"憂鬱な", "yuutsuna", "U sầu, chán nán", "Chăm chỉ, cần mẫn", "Mang tính cụ thể", "Cực kỳ, rất laf", "4"},
           {"朗らかな", "hogarakana", "Vui vẻ, sáng sủa", "Qúa sức, quá khả năng", "Mang tính kinh tế", "Thích hợp", "4"},
           {"敏感な", "binkanna", "Nhạy cảm", "Nổi tiếng", "Hạnh phúc, vui vẻ", "Trung thành", "4"},
           {"迅速な", "jinsokuna", "Nhanh chóng", "Giỏi", "Chăm chỉ, cần cù", "Lộng lẫy, rực rỡ", "4"},
           {"異常な", "ijona", "Khác thường", "Quan trọng", "Khỏe mạnh", "Chưa thuần thục", "4"},
           // 副詞
           {"きちっと", "kichitto", "Gọn gàng, ngay ngắn", "Cùng nhau", "Hoàn toàn", "Ngược lại", "4"},
           {"とっさに", "tossani", "Ngay lập tức", "Nhất định", "Khoảng chừng", "Khá là, tương đối ", "4"},
           {"突如", "totsujo", "Đột ngột", "Kể từ bây giờ", "Hầu hết, thường là", "Gọn gàng, chỉn chu", "4"},
           {"ずらっと", "zuratto", "Một loạt, một dãy", "Gần đây", "Tạm thời, nhất thời", "Vô cùng, cực kỳ", "4"},
           {"案の定", "annojo", "Quả nhiên, đúng như dự đoán", "Nhiều", "Cùng một lúc", "Tóm lại là, tức là", "4"},
           {"もとより", "motoyori", "Từ đầu", "Bằng cách nào", "Một ngày nào đó", "Lần đầu tiên", "4"},
           {"かかわらず", "kakawarazu", "Không quan tâm, bất chấp", "Lúc nào không biết", "Thêm nữa", "Trực tiếp", "4"},
           {"わざわざ", "wazawaza", "Cất công làm cho", "Luôn luôn", "Mãi mãi", "Nhiều, đầy ắp", "4"},
           {"いかに", "ikani", "Biết bao, như thế nào", "Lần này, lần tới", "Bất cứ lúc nào", "Không ...lắm", "4"},
           {"かつて", "katsute", "Trước kia, đã từng", "Đôi khi, thỉnh thoảng", "Cuối cùng thì", "Một lát sau", "4"},
           {"何より", "naniyori", "Hơn bất cứ thứ gì", "Rất, cực kỳ", "Vô ý, lơ đãng", "Như thế này", "4"},
           {"おろか", "oroka", "Ngu ngốc, dại dột", "Một chút nữa", "Có lẽ", "Nhất, dù thế nào vẫn", "4"},
           {"けっこう", "kekko", "Tương đối, khá là", "Không...lắm", "Cùng", "Để nguyên, như vốn dĩ", "4"},
           {"あえて", "aete", "Dám", "Bất cứ lúc nào", "Theo tỷ lệ", "Ví dụ", "4"},


            /*
            {"北海道", "札幌市", "長崎市", "福島市", "前橋市", "1"},
            {"青森県", "青森市", "広島市", "甲府市", "岡山市", "1"},
            {"岩手県", "盛岡市","大分市", "秋田市", "福岡市", "1"},
            {"宮城県", "仙台市", "水戸市", "岐阜市", "福井市", "1"},
            {"秋田県", "秋田市","横浜市", "鳥取市", "仙台市", "1"},
            {"山形県", "山形市","青森市", "山口市", "奈良市", "1"},
            {"福島県", "福島市", "盛岡市", "新宿区", "京都市", "1"},
            {"茨城県", "水戸市", "金沢市", "名古屋市", "奈良市", "2"},
            {"栃木県", "宇都宮市", "札幌市", "岡山市", "奈良市", "2"},
            {"群馬県", "前橋市", "福岡市", "松江市", "福井市", "2"},
            {"埼玉県", "さいたま市", "新潟市", "盛岡市", "水戸市", "2"},
            {"千葉県", "千葉市", "さいたま市", "横浜市", "松山市", "2"},
            {"東京都", "新宿区", "札幌市", "千葉市", "福島市", "2"},
            {"神奈川県", "横浜市", "宮崎市", "水戸市", "山口市", "2"},
            {"新潟県", "新潟市", "福島市", "前橋市", "大分市", "3"},
            {"富山県", "富山市", "山形市", "神戸市", "宇都宮市", "3"},
            {"石川県", "金沢市", "富山市", "熊本市", "山口市", "3"},
            {"福井県", "福井市", "長野市", "水戸市", "名古屋市", "3"},
            {"山梨県", "甲府市", "新潟市", "秋田市", "盛岡市", "3"},
            {"長野県", "長野市", "和歌山市", "那覇市", "山形市", "3"},
            {"岐阜県", "岐阜市", "千葉市", "京都市", "福井市", "3"},
            {"静岡県", "静岡市", "佐賀市", "宮崎市", "長崎市", "3"},
            {"愛知県", "名古屋市", "山口市", "秋田市", "長野市", "3"},
            {"三重県", "津市", "新宿区", "水戸市", "仙台市", "4"},
            {"滋賀県", "大津市", "鹿児島市", "福井市", "秋田市", "4"},
            {"京都府", "京都市", "甲府市", "大津市", "鹿児島市", "4"},
            {"大阪府", "大阪市", "さいたま市", "奈良市", "福島市", "4"},
            {"兵庫県", "神戸市", "宇都宮市", "大分市", "那覇市", "4"},
            {"奈良県", "奈良市", "甲府市", "千葉市", "広島市", "4"},
            {"和歌山県", "和歌山市", "盛岡市", "岐阜市", "金沢市", "4"},
            {"鳥取県", "鳥取市", "福井市", "那覇市", "徳島市", "5"},
            {"島根県", "松江市", "水戸市", "福島市", "大分市", "5"},
            {"岡山県", "岡山市", "大分市", "岐阜市", "鹿児島市", "5"},
            {"広島県", "広島市", "さいたま市", "長野市", "宮崎市", "5"},
            {"山口県", "山口市", "高知市", "大阪市", "水戸市", "5"},
            {"徳島県", "徳島市", "宇都宮市", "金沢市", "広島市", "5"},
            {"香川県", "高松市","富山市", "名古屋市", "鳥取市", "5"},
            {"愛媛県", "松山市", "山形市", "高松市", "奈良市", "5"},
            {"高知県", "高知市", "富山市", "松江市", "札幌市", "5"},
            {"福岡県", "福岡市", "高松市", "岡山市", "前橋市", "6"},
            {"佐賀県", "佐賀市", "秋田市", "水戸市", "富山市", "6"},
            {"長崎県", "長崎市", "松山市", "青森市", "大津市", "6"},
            {"熊本県", "熊本市", "名古屋市", "富山市", "千葉市", "6"},
            {"大分県", "大分市", "鳥取市", "津市", "甲府市", "6"},
            {"宮崎県", "宮崎市", "札幌市", "新潟市", "奈良市", "6"},
            {"鹿児島県", "鹿児島県", "和歌山市", "神戸市", "名古屋市", "6"},
            {"沖縄県", "那覇市", "熊本市", "高知市", "大津市", "6"}

             */
    };


    public QuizDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成
        db.execSQL("CREATE TABLE quiz (" +
                "question TEXT, alphabet TXT, answer TXT, choice1 TEXT," +
                "choice2 TXT, choice3 TXT, category TXT)");

        // トランザクション開始
        db.beginTransaction();
        try {
            // SQLを準備
            SQLiteStatement sql = db.compileStatement(
                    "INSERT INTO quiz (question, alphabet, answer, choice1, choice2, choice3, category)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)"
            );

            // クイズにデータを一行ずつ追加する
            for (int i=0; i < quizData.length; i++) {
                // Valueをセット
                // bindString(index, value)
                sql.bindString(1, quizData[i][0]); // 日本語
                sql.bindString(2, quizData[i][1]); // アルファベット
                sql.bindString(3, quizData[i][2]); // 正解
                sql.bindString(4, quizData[i][3]); // 選択肢１
                sql.bindString(5, quizData[i][4]); // 選択肢２
                sql.bindString(6, quizData[i][5]); // 選択肢３
                sql.bindString(7, quizData[i][6]); // カテゴリ

                sql.executeInsert();
            }

            // 成功
            db.setTransactionSuccessful();

        } catch (SQLException e) {
            // 失敗
            e.printStackTrace();

        } finally {
            // トランザクション終了
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS quiz");
            onCreate(db);
        }
    }
}
