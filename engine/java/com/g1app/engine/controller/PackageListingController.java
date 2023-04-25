package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseBase;
import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.*;
import com.g1app.engine.repositories.*;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.userdirectory.request.RequestByUUID;
import com.g1app.engine.userdirectory.request.RequestSearch;
import com.g1app.engine.userdirectory.response.*;
import com.g1app.engine.utils.CommonConstant;
import com.g1app.engine.utils.UrlConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(UrlConstant.PACKAGE)
public class PackageListingController {

    @Autowired
    PackageMasterRepository packageMasterRepository;
    @Autowired
    TestMasterRepository testMasterRepository;
    @Autowired
    PackageTestMasterRepository packageTestMasterRepository;
    @Autowired
    ProfileMasterRepository profileMasterRepository;
    @Autowired
    PackageProfileLinkRepository packageProfileLinkRepository;
    @Autowired
    TestDetailsMasterRepository testDetailsMasterRepository;
    @Autowired
    FilterMasterRepository filterMasterRepository;
    @Autowired
    FilterCategoryMasterRepository filterCategoryMasterRepository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserRepository repository;

    @PostMapping("/addPackageMaster")
    public ResponseEntity<ResponseBase> addPackageInMaster(@RequestBody PackageMaster body) {
        if (body != null) {
            packageMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addTestMaster")
    public ResponseEntity<ResponseBase> addTestsInMaster(@RequestBody TestMaster body) {
        if (body != null) {
            testMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addProfileMaster")
    public ResponseEntity<ResponseBase> addProfileInMaster(@RequestBody ProfileMaster body) {
        if (body != null) {
            profileMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addPackageTestMaster")
    public ResponseEntity<ResponseBase> addPackageTestMaster(@RequestBody PackageTestMaster body) {
        if (body != null) {
            packageTestMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-filterMaster")
    public ResponseEntity<ResponseBase> addFilterMaster(@RequestBody FilterMaster body) {
        if (body != null) {
            if(body.getSubCategory() == null){
                body.setSubCategoryId(UUID.randomUUID());
            }
            filterMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addPackageProfileLink")
    public ResponseEntity<ResponseBase> addPackageProfileLink(@RequestBody PackageProfileLink body) {
        if (body != null) {
            packageProfileLinkRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addTestDetailsMaster")
    public ResponseEntity<ResponseBase> addTestDetailsMaster(@RequestBody TestDetailsMaster body) {
        if (body != null) {
            testDetailsMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-filterCategory")
    public ResponseEntity<ResponseBase> addFilterCategory(@RequestBody FilterCategoryMaster body) {
        if (body != null) {
            if(body.categoryId==null){
                body.setCategoryId(UUID.randomUUID());
            }
            filterCategoryMasterRepository.save(body);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(UrlConstant.PACKAGES_LIST)
    public ResponseEntity<ResponsePackageListing> getPackageList() {
        ResponsePackageListing responsePackageListing = new ResponsePackageListing();
        List<PackageMaster> listPackages = packageMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
        if (listPackages.isEmpty()) {
            responseMessage(responsePackageListing, ResponseStatusMessages.NO_PACKAGE_FOUND, ResponseStatusCode.NOT_FOUND);
            return new ResponseEntity<>(responsePackageListing, HttpStatus.OK);
        }
        for (PackageMaster pm : listPackages) {
            addingPackagesDetails(responsePackageListing, pm);
        }
        responseMessage(responsePackageListing, ResponseStatusMessages.SUCCESS, ResponseStatusCode.SUCCESS);
        return new ResponseEntity<>(responsePackageListing, HttpStatus.OK);
    }


    @PostMapping(UrlConstant.TESTS_LIST)
    public ResponseEntity<ResponseTestListing> getTestList() {
        ResponseTestListing responseTestListing = new ResponseTestListing();
        List<TestMaster> listTest = testMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
        if (listTest.isEmpty()) {
            responseTestListing.message = ResponseStatusMessages.NO_TEST_FOUND.getValue();
            responseTestListing.statusCode = ResponseStatusCode.NOT_FOUND.getValue();
            return new ResponseEntity<>(responseTestListing, HttpStatus.OK);
        }
        for (TestMaster tm : listTest) {
            ResponseTestInfo info = new ResponseTestInfo();
            info.testID = tm.getTestId();
            info.testPrice = tm.getTestPrice();
            info.testTitle = tm.getTestTitle();
            info.testDescription = tm.getTestDescription();
            info.testReportTime = tm.getReportTime();
            info.testMainElement = tm.getTestMainElement();
            info.discountType = tm.getTestDiscountType();
            info.testDiscountValue = tm.getTestDiscountValue();
            info.recommendedFor = tm.getTestRecommendedFor();
            info.sortOrder = tm.getSortOrder();
            info.filterValue = tm.getFilterValue();
            responseTestListing.test.add(info);
        }
        responseTestListing.message = ResponseStatusMessages.SUCCESS.getValue();
        responseTestListing.statusCode = ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(responseTestListing, HttpStatus.OK);
    }

//    @PostMapping(UrlConstant.LIST_PACKAGES_TEST_PROFILES)
//    public ResponseEntity<ResponseAllPackagesList> getAllList() {
//        ResponseAllPackagesList response = new ResponseAllPackagesList();
//        List<PackageMaster> listPackages = packageMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
//
//        for (PackageMaster pm : listPackages) {
//            ResponseAllPackageInfo info = new ResponseAllPackageInfo();
//            info.id = pm.getPackageId();
//            info.price = pm.getPackagePrice();
//            info.description = pm.getPackageDescription();
//            info.title = pm.getPackageTitle();
//            info.discountValue = pm.getPackageDiscountValue();
//            info.discountType = pm.getPackageDiscountType();
//            info.reportTime = pm.getPackageReportTime();
//            info.recommendedFor = pm.getPackageRecommendedFor();
//            info.sortOrder = pm.getSortOrder();
//            info.totalTestCount = pm.getTotalTestCount();
//            info.type = "package";
//            response.result.add(info);
//        }
//
//        List<ProfileMaster> listProfile = profileMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
//        for (ProfileMaster pm : listProfile) {
//            ResponseAllPackageInfo info = new ResponseAllPackageInfo();
//            info.id = pm.getProfileId();
//            info.price = pm.getProfilePrice();
//            info.description = pm.getProfileDescription();
//            info.title = pm.getProfileTitle();
//            info.discountValue = pm.getProfileDiscountValue();
//            info.discountType = pm.getProfileDiscountType();
//            info.reportTime = pm.getProfileReportTime();
//            info.recommendedFor = pm.getProfileRecommendedFor();
//            info.sortOrder = pm.getSortOrder();
//            info.type = "profile";
//            info.totalTestCount= pm.getTotalTestCount();
//            response.result.add(info);
//        }
//
//        List<TestMaster> listTest = testMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
//        for (TestMaster pm : listTest) {
//            ResponseAllPackageInfo info = new ResponseAllPackageInfo();
//            info.id = pm.getTestId();
//            info.price = pm.getTestPrice();
//            info.description = pm.getTestDescription();
//            info.title = pm.getTestTitle();
//            info.discountValue = pm.getTestDiscountValue();
//            info.discountType = pm.getTestDiscountType();
//            info.reportTime = pm.getReportTime();
//            info.recommendedFor = pm.getTestRecommendedFor();
//            info.sortOrder = pm.getSortOrder();
//            info.type = "test";
//            response.result.add(info);
//        }
//
//        response.message = ResponseStatusMessages.SUCCESS.getValue();
//        response.statusCode = ResponseStatusCode.SUCCESS.getValue();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @PostMapping(UrlConstant.PROFILES_LIST)
    public ResponseEntity<ResponseProfileListing> getProfileList() {
        ResponseProfileListing response = new ResponseProfileListing();
        List<ProfileMaster> listProfile = profileMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
        if (listProfile.isEmpty()) {
            response.message = ResponseStatusMessages.NO_PACKAGE_FOUND.getValue();
            response.statusCode = ResponseStatusCode.NOT_FOUND.getValue();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        for (ProfileMaster pm : listProfile) {
            ResponseProfileInfo info = new ResponseProfileInfo();
            info.profilePrice = pm.getProfilePrice();
            info.profileDescription = pm.getProfileDescription();
            info.discountType = pm.getProfileDiscountType();
            info.profileDiscountValue = pm.getProfileDiscountValue();
            info.profileTitle = pm.getProfileTitle();
            info.profileMainElement = pm.getProfileMainElement();
            info.recommendedFor = pm.getProfileRecommendedFor();
            info.profileReportTime = pm.getProfileReportTime();
            info.sortOrder = pm.getSortOrder();
            info.totalTestCount = pm.getTotalTestCount();
            response.profile.add(info);
        }
        response.message = ResponseStatusMessages.SUCCESS.getValue();
        response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_TEST_DETAILS)
    public ResponseEntity<ResponseTest> getTest(@RequestBody RequestByUUID requestBody) {
        UUID testId = requestBody.value;

        ResponseTest response = new ResponseTest();

        if (testId != null) {
            TestMaster testMaster = testMasterRepository.findByTestId(testId);
            if (testMaster != null) {
                response.testTitle = testMaster.getTestTitle();
                response.testDescription = testMaster.getTestDescription();
                response.discountType = testMaster.getTestDiscountType();
                response.testPrice = testMaster.getTestPrice();
                response.testDiscountValue = testMaster.getTestDiscountValue();
                response.testMainElement = testMaster.getTestMainElement();
                response.testReportTime = testMaster.getReportTime();
                response.recommendedFor = testMaster.getTestRecommendedFor();

                response.message = ResponseStatusMessages.SUCCESS.getValue();
                response.statusCode = ResponseStatusCode.SUCCESS.getValue();

            } else {
                response.message = ResponseStatusMessages.NOT_FOUND.getValue();
                response.statusCode = ResponseStatusCode.NOT_FOUND.getValue();
            }

        } else {
            response.message = ResponseStatusMessages.PASS_TEST_ID.getValue();
            response.statusCode = ResponseStatusCode.NOT_FOUND.getValue();

        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @param requestBody
     * @return packages details
     */
    @PostMapping(UrlConstant.GET_PACKAGE_DETAILS)
    public ResponseEntity<ResponsePackageTestList> getPackages(@RequestBody  RequestByUUID requestBody) {
        UUID packageId = requestBody.value;
        ResponsePackageTestList response = new ResponsePackageTestList();

        if (packageId != null) {
            PackageMaster packageMaster = packageMasterRepository.findByPackageIdAndIsActiveAndIsDeleted(packageId,true,false);
            if (packageMaster != null ) {
                response.packageTitle = packageMaster.getPackageTitle();
                response.packageDescription = packageMaster.getPackageDescription();
                response.discountType = packageMaster.getPackageDiscountType();
                response.packagePrice = packageMaster.getPackagePrice();
                response.packageDiscountValue = packageMaster.getPackageDiscountValue();
                response.packageReportTime = packageMaster.getPackageReportTime();
                response.recommendedFor = packageMaster.getPackageRecommendedFor();
                response.totalTestCount = packageMaster.getTotalTestCount();

                List<PackageTestMaster> listPackageTest = packageTestMasterRepository.findByPackageIdAndIsActiveAndIsDeleted(packageId, true, false);

                for (PackageTestMaster ptm : listPackageTest) {
                    ResponsePackageTestInfo info = new ResponsePackageTestInfo();
                    info.testDisplayName = ptm.getTestDisplayName();
                    info.testId = ptm.getTestId();

                    List<TestDetailsMaster> listTestDetailsMaster = testDetailsMasterRepository.findByTestId(info.testId);
                    for(TestDetailsMaster tdm : listTestDetailsMaster){
                        ResponseTestDetails responseTestDetails = new ResponseTestDetails();
                        responseTestDetails.testDetails = tdm.getTestDetails();
                    }
                    response.testList.add(info);
                }

                List<PackageProfileLink> listProfile = packageProfileLinkRepository.findByPackageIdAndIsActiveAndIsDeleted(packageId,true,false);
                for (PackageProfileLink ptm : listProfile) {
                    ResponseProfileDetails info = new ResponseProfileDetails();
                    info.profileId = ptm.getProfileId();
                    ProfileMaster profileMaster = profileMasterRepository.findByProfileId(info.profileId);
                    if(profileMaster != null){
                        String profileDes = profileMaster.getProfileTitle();
                        info.ProfileDisplayName = profileDes;
                    }
                    response.profileList.add(info);
                }

                response.message = ResponseStatusMessages.SUCCESS.getValue();
                response.statusCode = ResponseStatusCode.SUCCESS.getValue();

            } else {
                response.message = ResponseStatusMessages.NOT_FOUND.getValue();
                response.statusCode = ResponseStatusCode.NOT_FOUND.getValue();
            }

        } else {
            response.message = ResponseStatusMessages.NOT_FOUND.getValue();
            response.statusCode = ResponseStatusCode.NOT_FOUND.getValue();

        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @param request
     * @return search results for packages,profiles,tests
     */
    @PostMapping(UrlConstant.COMMON_SEARCH)
    public ResponseEntity<ResponseSearchListing> getDiagnosisPastHistory(@RequestBody RequestSearch request) {
        ResponseSearchListing response = new ResponseSearchListing();
        String searchValue = request.searchValue;
        List<PackageMaster> packageList = packageMasterRepository.getPackages(searchValue);
        for(PackageMaster pm: packageList){
            ResponseSearchInfo info = new ResponseSearchInfo();
            info.id = pm.getPackageId();
            info.title = pm.getPackageTitle();
            info.description = pm.getPackageDescription();
            info.tags = pm.getPackageTags();
            info.type = CommonConstant.PACKAGE;
            response.results.add(info);
        }

        List<ProfileMaster> profileList = profileMasterRepository.getProfiles(searchValue);
        for(ProfileMaster pm: profileList){
            ResponseSearchInfo info = new ResponseSearchInfo();
            info.id = pm.getProfileId();
            info.title = pm.getProfileTitle();
            info.description = pm.getProfileDescription();
            info.tags = pm.getProfileTags();
            info.type = CommonConstant.PROFILE;
            response.results.add(info);
        }

        List<TestMaster> testList = testMasterRepository.getTests(searchValue);
        for(TestMaster tm: testList){
            ResponseSearchInfo info = new ResponseSearchInfo();
            info.id = tm.getTestId();
            info.title = tm.getTestTitle();
            info.description = tm.getTestDescription();
            info.tags = tm.getTestTags();
            info.type = CommonConstant.TEST;
            response.results.add(info);
        }

        response.message    = ResponseStatusMessages.SUCCESS.getValue();
        response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * @param requestBody
     * @return packages details which aligned with test's
     */
    @PostMapping(UrlConstant.PACKAGES_FOR_TEST)
    public ResponseEntity<ResponsePackageListing> getPackagesForTest(@RequestBody RequestByUUID requestBody){
        ResponsePackageListing response = new ResponsePackageListing();
        if(requestBody != null){

            List<PackageTestMaster> ls = packageTestMasterRepository.getPackages(requestBody.value);
            if(ls !=null){
                for (PackageTestMaster pm : ls) {
                    UUID packageId = pm.getPackageId();
                    PackageMaster packageMaster = packageMasterRepository.findByPackageIdAndIsActiveAndIsDeleted(packageId,true,false);
                    if(packageMaster != null) {
                        addingPackagesDetails(response, packageMaster);
                    }
                }
                responseMessage(response, ResponseStatusMessages.SUCCESS, ResponseStatusCode.SUCCESS);
            }else {
                responseMessage(response, ResponseStatusMessages.NOT_FOUND, ResponseStatusCode.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_FILTERS)
    public ResponseEntity<ResponseFilterList> getFilterListing(){
        ResponseFilterList response = new ResponseFilterList();

        List<FilterCategoryMaster> listCategory = filterCategoryMasterRepository.findAll();
        if (listCategory.isEmpty()) {
            response.message = ResponseStatusMessages.NO_PACKAGE_FOUND.getValue();
            response.statusCode = ResponseStatusCode.NOT_FOUND.getValue();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        for (FilterCategoryMaster cm : listCategory) {
            ResponseFilterInfo info = new ResponseFilterInfo();
            info.categoryId = String.valueOf(cm.getCategoryId());
            info.categoryName = cm.getCategoryName();
            info.categoryType = cm.getCategoryType();

            List<FilterMaster> listSubCategory = filterMasterRepository.findByCategoryId(String.valueOf(cm.getCategoryId()));
            if(listSubCategory != null) {
                for (FilterMaster fm : listSubCategory) {
                    ResponseFilterSubCategory info1 = new ResponseFilterSubCategory();
                    info1.subCategoryId = fm.getSubCategoryId();
                    info1.subCategoryName = fm.getSubCategory();
                    info.subCategory.add(info1);
                }
            }
            response.category.add(info);
            response.message = ResponseStatusMessages.SUCCESS.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * On the Bases of Filter value return the recommended packages/tests/profiles
     * @return packages with test
     */

    public String convertArrayToCsv(int[] IncomingArray)
    {
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<IncomingArray.length;i++)
        {
            sb=sb.append(IncomingArray[i]);
            if(i != IncomingArray.length-1)
            {
                sb.append(",");
            }
        }
        return sb.toString();
    }//

    @PostMapping(UrlConstant.FILTER_SEARCH)
    public ResponseEntity<Object> test(@RequestBody RequestSearch request) {

        String searchValue = request.searchValue;
        int[] filterValues = request.filterValues;

        String csv =  convertArrayToCsv( filterValues);

        List<TestMaster> tm = testMasterRepository.query("Vitamins K");


        ResponseList response = new ResponseList();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private void addingPackagesDetails(ResponsePackageListing responsePackageListing, PackageMaster pm) {
        ResponsePackageInfo info = new ResponsePackageInfo();
        info.packageID = pm.getPackageId();
        info.packagePrice = pm.getPackagePrice();
        info.packageDescription = pm.getPackageDescription();
        info.packageTitle = pm.getPackageTitle();
        info.packageDiscountValue = pm.getPackageDiscountValue();
        info.discountType = pm.getPackageDiscountType();
        info.packageReportTime = pm.getPackageReportTime();
        info.recommendedFor = pm.getPackageRecommendedFor();
        info.sortOrder = pm.getSortOrder();
        responsePackageListing.packages.add(info);
    }

    private void responseMessage(ResponsePackageListing responsePackageListing, ResponseStatusMessages success, ResponseStatusCode success1) {
        responsePackageListing.message = success.getValue();
        responsePackageListing.statusCode = success1.getValue();
    }


    @PostMapping(UrlConstant.LIST_PACKAGES_TEST_PROFILES)
    public ResponseEntity<ResponseAllPackagesList> getAllItemList(@RequestHeader("Authorization") String token) {
        ResponseAllPackagesList response = new ResponseAllPackagesList();

        if (token == null || token.isEmpty()) {
            response.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        String bearer = tokenProvider.resolveToken(token);

        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            UUID customerId = UUID.fromString(tokenProvider.getUsername(bearer));
            User user = repository.findByCustomerID(customerId);
            if (user != null) {
                String userMobileNo = user.getMobileNumber();
                if (userMobileNo == null) {
                    response.message = "Please Update your Mobile. Number";
                    response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }
            } else {
                response.message = ResponseStatusMessages.USER_NOT_FOUND.getValue();
                response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            }
        }

            List<PackageMaster> listPackages = packageMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);

            for (PackageMaster pm : listPackages) {
                ResponseAllPackageInfo info = new ResponseAllPackageInfo();
                info.id = pm.getPackageId();
                info.price = pm.getPackagePrice();
                info.description = pm.getPackageDescription();
                info.title = pm.getPackageTitle();
                info.discountValue = pm.getPackageDiscountValue();
                info.discountType = pm.getPackageDiscountType();
                info.reportTime = pm.getPackageReportTime();
                info.recommendedFor = pm.getPackageRecommendedFor();
                info.sortOrder = pm.getSortOrder();
                info.totalTestCount = pm.getTotalTestCount();
                info.type = "package";
                response.result.add(info);
            }

            List<ProfileMaster> listProfile = profileMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
            for (ProfileMaster pm : listProfile) {
                ResponseAllPackageInfo info = new ResponseAllPackageInfo();
                info.id = pm.getProfileId();
                info.price = pm.getProfilePrice();
                info.description = pm.getProfileDescription();
                info.title = pm.getProfileTitle();
                info.discountValue = pm.getProfileDiscountValue();
                info.discountType = pm.getProfileDiscountType();
                info.reportTime = pm.getProfileReportTime();
                info.recommendedFor = pm.getProfileRecommendedFor();
                info.sortOrder = pm.getSortOrder();
                info.type = "profile";
                info.totalTestCount = pm.getTotalTestCount();
                response.result.add(info);
            }

            List<TestMaster> listTest = testMasterRepository.findByIsActiveAndIsDeletedOrderBySortOrderDesc(true, false);
            for (TestMaster pm : listTest) {
                ResponseAllPackageInfo info = new ResponseAllPackageInfo();
                info.id = pm.getTestId();
                info.price = pm.getTestPrice();
                info.description = pm.getTestDescription();
                info.title = pm.getTestTitle();
                info.discountValue = pm.getTestDiscountValue();
                info.discountType = pm.getTestDiscountType();
                info.reportTime = pm.getReportTime();
                info.recommendedFor = pm.getTestRecommendedFor();
                info.sortOrder = pm.getSortOrder();
                info.type = "test";
                response.result.add(info);
            }


        response.message = ResponseStatusMessages.SUCCESS.getValue();
        response.statusCode = ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
