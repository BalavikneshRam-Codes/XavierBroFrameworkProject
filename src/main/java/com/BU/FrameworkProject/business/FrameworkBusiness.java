package com.BU.FrameworkProject.business;

import com.BU.FrameworkProject.Entity.*;
import com.BU.FrameworkProject.emailTemplate.EmailTemplate;
import com.BU.FrameworkProject.repository.*;
import com.BU.FrameworkProject.vo.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.model.geom.Path;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import io.micrometer.common.util.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FrameworkBusiness implements IFrameworkBusiness { ;
    @Autowired
    private FrameworkRatingRepo frameworkRatingRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FrameworkRepo frameworkRepo;
    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private FrameworkQuestionRepo frameworkQuestionRepo;
    @Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private TestRepo testRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String userName;
    @Override
    public FrameworkVO saveFrameworkBO(FrameworkVO frameworkVO) throws Exception {
        if (validation(frameworkVO)) {
            try {
                Framework framework = convertVOtoEntityAndSave(frameworkVO);
                frameworkRepo.save(framework);
            } catch (Exception e) {
                frameworkVO.setMessage(e.getMessage());
                throw new Exception(e.getMessage());
            }
        }
        return frameworkVO;
    }


    @Override
    public FrameworkVO findFrameworkBO(Long id) throws Exception {
        if(frameworkRepo.existsById(id)){
            Framework framework = findByIdFramework(id);
            return convertEntityToVO(framework);
        }else{
            throw new Exception("framework Id does not exists");
        }
    }

    @Override
    public String saveUser(UserVo userVo) {
        User user = new User();
        user.setUseEmail(userVo.getUser_email());
        user.setUserName(userVo.getUser_name());
        userRepo.save(user);
        return "User Saved successfully";
    }

    @Override
    public TestVO takeTest(TestVO testVO) throws Exception {
        try {
            Test test = convertEntityToVoTest(testVO);
            testRepo.save(test);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return testVO;
    }

    @Override
    public PercentageVO getScoreByTestId(PercentageVO percentageVO) throws Exception {
        try {
            setPercentageProperties(percentageVO);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
         return percentageVO;
    }

    @Override
    public FileVO getFileStatus() throws Exception {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("User Data");
            List<User> users = userRepo.findAll();


            AtomicInteger rowCount = new AtomicInteger(0);
            AtomicInteger colCount = new AtomicInteger(0);

            Row rowHeader = sheet.createRow(rowCount.getAndIncrement());

            getHeader().forEach(value -> {
                Cell cell = rowHeader.createCell(colCount.getAndIncrement());
                cell.setCellValue(value);
            });

            users.stream()
                    .sorted(Comparator.comparing(User::getUserId))
                    .forEach(user -> {
                        int columnCount = 0;
                        Row row = sheet.createRow(rowCount.getAndIncrement());
                        row.createCell(columnCount++).setCellValue(user.getUserId());
                        row.createCell(columnCount++).setCellValue(user.getUserName());
                        row.createCell(columnCount++).setCellValue(user.getUseEmail());
                    });
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\balavikneshram.j\\Desktop\\UserData\\UserSheet.xlsx");

            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();
            FileVO fileVO = new FileVO();
            fileVO.setFileStatus("Uploaded");
            return fileVO;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    public FileVO getCSVForUser() throws Exception {
        try {
            FileWriter fileOutputStream = new FileWriter("C:\\Users\\balavikneshram.j\\Desktop\\UserData\\userCsv.csv");
            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            List<User> users = userRepo.findAll();

            getHeader().forEach(header->{
                printWriter.print(header+", ");
            });
            printWriter.println();

            users.stream()
                    .sorted(Comparator.comparing(User::getUserId))
                    .forEach(user -> {
                        printWriter.print(user.getUserId()+", ");
                        printWriter.print(user.getUserName()+", ");
                        printWriter.print(user.getUseEmail()+", ");
                        printWriter.println();
                    });
             fileOutputStream.close();
             printWriter.flush();
            FileVO fileVO = new FileVO();
            fileVO.setFileStatus("Uploaded");
            return fileVO;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Override
    public String sendEmailUser(PercentageVO percentageVO) throws Exception {
        try {
            Test test = testRepo.findById(percentageVO.getTestId()).orElseThrow();
            User user = findByUserId(test.getUserId().getUserId());
            PercentageVO percentageVO_ = getPercentageByTestId(percentageVO.getTestId());
            percentageVO_.setTestId(percentageVO.getTestId());

            MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            mailSetter(user,percentageVO_,mimeMailMessage);

            javaMailSender.send(mimeMailMessage);
            return "Sent Successfully";
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private void mailSetter(User user, PercentageVO percentageVO, MimeMessage mimeMessage) throws MessagingException, IOException {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(userName);
        mimeMessageHelper.setTo(user.getUseEmail());

        Test test = testRepo.findById(percentageVO.getTestId()).orElseThrow();

        mimeMessageHelper.setSubject(test.getFrameworkId().getFrameworkName());

        String content = readFileAsString(EmailTemplate.demoTemplate.getFilePath());
        content = content.replace("${userName}",user.getUserName());
        content = content.replace("${percentage}",String.format("%.2f",percentageVO.getPercentage()));
        content = content.replace("${TotalMarks}",percentageVO.getTotalMarks()+"");
        content = content.replace("${img}","https://images.pexels.com/photos/35537/child-children-girl-happy.jpg?cs=srgb&dl=pexels-bess-hamiti-83687-35537.jpg&fm=jpg");

        content = content.replace("${MaxMark}",percentageVO.getMaxMarks()+"");

        File file = new File("C:\\Users\\balavikneshram.j\\Downloads\\testSheet.jpg");
        if(!file.exists()){
            System.out.println("File does not exist: " + file.getAbsolutePath());
        }
        mimeMessageHelper.addAttachment(file.getName(),file);
        mimeMessageHelper.setText(content,true);
    }


    private String readFileAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }


    private List<String> getHeader(){
        return new ArrayList<>(Arrays.asList("userId","UserName","UserEmail"));
    }


    private PercentageVO setPercentageProperties(PercentageVO percentageVO) throws Exception {
        Test test = testRepo.findById(percentageVO.getTestId()).orElseThrow();
        PercentageVO percentageVO_ = getPercentageByTestId(test.getTestId());
        percentageVO.setPercentage(percentageVO_.getPercentage());
        percentageVO.setTotalMarks(percentageVO_.getTotalMarks());
        percentageVO.setUserId(test.getUserId().getUserId());
        percentageVO.setMaxMarks(percentageVO_.getMaxMarks());
        percentageVO.setUserName(test.getUserId().getUserName());
        return percentageVO;
    }
    private PercentageVO getPercentageByTestId(Long testId) throws Exception {
        Test test = testRepo.findById(testId).orElseThrow();
       Long max =  test.getFrameworkId().getFrameworkRatings()
               .stream()
               .max(Comparator.comparing(FrameworkRating::getFrameworkRatingScore))
               .get()
               .getFrameworkRatingScore();

       int numQuestion = test.getQuestionAnswers().size();

        AtomicLong totalScore = new AtomicLong();
        test.getQuestionAnswers().forEach(
                questionAnswer -> totalScore.addAndGet(questionAnswer.getFrameworkRatings().getFrameworkRatingScore())
        );

       double percentage =((totalScore.doubleValue() / (max * numQuestion) ) * 100.0);

        PercentageVO percentageVO = new PercentageVO();
        percentageVO.setMaxMarks(max*numQuestion);
        percentageVO.setTotalMarks(totalScore.get());
        percentageVO.setPercentage(Double.parseDouble(String.format("%.2f",percentage)));
       return percentageVO;
    }
    public Test convertEntityToVoTest(TestVO testVO) throws Exception {
        Test test = new Test();
        if(testRepo.existsByUserId(test.getUserId())){
            Framework framework = findByIdFramework(testVO.getFrameworkId().getFrameworkId());
            test.setFrameworkId(framework);

            User user = findByUserId(testVO.getUserId().getUser_id());
            test.setUserId(user);

            Set<QuestionAnswer> questionAnswers = questionAnswers(testVO,test);
            test.setQuestionAnswers(questionAnswers);
        }
        return null;
    }

    private Set<QuestionAnswer> questionAnswers(TestVO testVO,Test test){
        Set<QuestionAnswer> questionAnswerVOS = new HashSet<>();
        testVO.getQuestionAnswers()
                .forEach(questionAnswerVO ->{
                    QuestionAnswer  questionAnswer = new QuestionAnswer();
                    questionAnswer.setTest(test);
                    try {
                        questionAnswer.setFrameworkRatings(findByIdFrameworkRating(questionAnswerVO.getFrameworkRatingVO().getFrameworkRatingId()));
                        questionAnswer.setFrameworkQuestionId(findByIdFrameworkQuestion(questionAnswerVO.getFrameworkQuestionVO().getFrameworkQuestionId()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    questionAnswerVOS.add(questionAnswer);
                });
        return questionAnswerVOS;
    }

    private FrameworkRating findByIdFrameworkRating(Long id) throws Exception {
        return frameworkRatingRepo.findById(id).orElseThrow(()->new Exception());
    }

    private FrameworkQuestion findByIdFrameworkQuestion(Long id) throws Exception {
        return frameworkQuestionRepo.findById(id).orElseThrow(()-> new Exception());
    }

    private User findByUserId(Long id) throws Exception {
        return userRepo.findById(id).orElseThrow(()->new Exception());
    }

    private Framework findByIdFramework(Long id){
        if(frameworkRepo.existsById(id)){
            return frameworkRepo.findById(id).orElseThrow();
        }
        return null;
    }
    private Framework convertVOtoEntityAndSave(FrameworkVO frameworkVO) {
        Framework framework = new Framework();
        framework.setFrameworkDate(new Date());
        framework.setFrameworkName(frameworkVO.getFrameworkName());
        framework.setFrameworkStatus("Active");
        Set<FrameworkQuestion> frameworkQuestions = frameworkQuestions(frameworkVO,framework);
        framework.setFrameworkQuestions(frameworkQuestions);
        Set<FrameworkRating> frameworkRatings = frameworkRatingsSave(frameworkVO,framework);
        framework.setFrameworkRatings(frameworkRatings);
        return framework;
    }

    private Set<FrameworkRating> frameworkRatingsSave(FrameworkVO frameworkVO,Framework framework){
        Set<FrameworkRating> frameworkRatings = new LinkedHashSet<>();
        frameworkVO.getFrameworkRatings()
                .forEach(frameworkRatingVO -> {
            FrameworkRating frameworkRating = new FrameworkRating();
            Rating rating = findByIdRating(frameworkRatingVO.getRatingVO().getRatingId());
            frameworkRating.setFramework(framework);
            frameworkRating.setRating(rating);
            frameworkRating.setFrameworkRatingScore(frameworkRatingVO.getFrameworkRating_Score());
            frameworkRatings.add(frameworkRating);
        });
        return frameworkRatings;
    }
    private Rating findByIdRating(Long id){
        if(ratingRepo.existsById(id)) {
            return ratingRepo.findById(id).orElseThrow();
        }return null;
    }

    private Set<FrameworkQuestion> frameworkQuestions(FrameworkVO frameworkVO,Framework framework){
        Set<FrameworkQuestion> frameworkQuestions = new LinkedHashSet<>();
        frameworkVO.getFrameworkQuestions()
                .forEach(frameworkQuestionVO -> {
                    FrameworkQuestion frameworkQuestion = new FrameworkQuestion();
                    frameworkQuestion.setFrameworkQuestionStatus("Active");

                    Question question = findByID(frameworkQuestionVO);
                    frameworkQuestion.setQuestion(question);
                    frameworkQuestion.setFramework(framework);
                    frameworkQuestions.add(frameworkQuestion);
                });
        return frameworkQuestions;
    }
    private Question findByID(FrameworkQuestionVO frameworkQuestionVO){
        return questionRepo.findById(frameworkQuestionVO.getQuestion().getQuestionId()).orElse(null);
    }

    private FrameworkVO convertEntityToVO(Framework framework) {
        FrameworkVO frameworkVO_ = new FrameworkVO();
        frameworkVO_.setFrameworkId(framework.getFrameworkId());
        frameworkVO_.setFrameworkName(framework.getFrameworkName());
        frameworkVO_.setFrameworkDate(framework.getFrameworkDate());
        frameworkVO_.setFrameworkStatus(framework.getFrameworkStatus());

        Set<FrameworkRatingVO> frameworkRatingVOS = frameworkRating(framework);
//        frameworkVO_.setFrameworkRatings(frameworkRatingVOS);

        Set<FrameworkQuestionVO> frameworkQuestionVOS = frameworkQuestionsToVo(framework,frameworkRatingVOS);
        frameworkVO_.setFrameworkQuestions(frameworkQuestionVOS);

        return frameworkVO_;
    }

    private Set<FrameworkRatingVO> frameworkRating(Framework framework){
        Set<FrameworkRatingVO> frameworkQuestionVOS = new LinkedHashSet<>();

        framework.getFrameworkRatings()
                .forEach(rating -> {

            FrameworkRatingVO ratingVO = new FrameworkRatingVO();
            ratingVO.setFrameworkRatingId(rating.getFrameworkRatingId());
            ratingVO.setFrameworkRating_Score(rating.getFrameworkRatingScore());
            RatingVO ratingDetailsVO = new RatingVO();
            ratingDetailsVO.setRatingId(rating.getRating().getRatingId());
            ratingDetailsVO.setRatingName(rating.getRating().getRatingName());

            ratingVO.setRatingVO(ratingDetailsVO);
            frameworkQuestionVOS.add(ratingVO);
        });
        return frameworkQuestionVOS;
    }

    private Set<FrameworkQuestionVO> frameworkQuestionsToVo(Framework framework,Set<FrameworkRatingVO> frameworkRatingVOS) {
        Set<FrameworkQuestionVO> frameworkQuestionVOS = new LinkedHashSet<>();
        framework.getFrameworkQuestions()
                .stream()
                .sorted(Comparator.comparingLong(value->value.getFrameworkQuestionId()))
                .forEach(question -> {
                    FrameworkQuestionVO questionVO = new FrameworkQuestionVO();
                    questionVO.setFrameworkQuestionId(question.getFrameworkQuestionId());
                    questionVO.setFrameworkQuestionStatus(question.getFrameworkQuestionStatus());

                    QuestionVO questionDetailsVO = new QuestionVO();
                    questionDetailsVO.setQuestionId(question.getQuestion().getQuestionId());
                    questionDetailsVO.setQuestionName(question.getQuestion().getQuestionName());

                    List<FrameworkRatingVO> ratingVOS = new ArrayList<>(frameworkRatingVOS);
                    questionVO.setFrameworkRatingVOS(ratingVOS);

                    questionVO.setQuestion(questionDetailsVO);
                    frameworkQuestionVOS.add(questionVO);
                });
        return frameworkQuestionVOS;
    }
    private boolean validation(FrameworkVO frameworkVO) {
        if (StringUtils.isBlank(frameworkVO.getFrameworkName())) {
            frameworkVO.setMessage("framework contains whiteSpaces or Empty");
            return false;
        }
        return true;
    }
}
