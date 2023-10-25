$(function(){
    // alert("하이");

    let price;
    let priceAll = 0;
    let requirements;

    let clientKey = 'test_ck_DLJOpm5QrlxzAkdbmPdrPNdxbWnY' // 테스트용 클라이언트 키
    let tossPayments = TossPayments(clientKey)

    $("#searchAddress").on('click',searchAddressFunc); //우편번호 불러오기
    // $('#orderBtn').on('click', paymentStart); //주문 및 결제 시작

    $(".price").text(function(index, element) {

        console.log("index : ", index, " element : ", element)

        price = Number(element);
        priceAll += price;

        $('#priceAll').text(priceAll.toLocaleString('ko-KR') + '원');
        $('#orderBtn').text(priceAll.toLocaleString('ko-KR') + '원 주문하기');
    });

    // $("select[name=deliveryRequest]").change(function(){
    //     requirements = $("select[name=deliveryRequest] option:selected").text();
    //
    //     console.log(requirements); //text값 가져오기
    // });

    $('#orderBtn').click(function (){

        let orderer = $('#inputOrderer').val(); //주문자
        let ordererPhone = $('#inputPhone').val();
        let email = $('#inputEmail').val();

        let recipient = $('#inputRecipient').val(); //받는 사람
        let recipientPhone = $('#recipientPhone').val(); //받는 사람 번호
        let zipcode = $('#zipcodeView').val(); //우편 번호
        let city = $('#cityView').val(); //기본 주소
        let street = $('#street').val(); //나머지 주소

        requirements = $("select[name=deliveryRequest] option:selected").text(); //요청사항

        // let deliverySave = $('#deliverySave').is(':checked');

        console.log("email : ", email, " name : ", orderer, " ordererPhone : ", ordererPhone, " productName : ", productName, " cartLenth : ", cartLenth,
            " recipient : ", recipient, " recipientPhone : ", recipientPhone, " zipcode : ", zipcode, " city : ", city, " street : ", street
            , " requirements : ", requirements);

        let orderData = {
            'member': {
                'id':customerKey
            },
            'status' : 'AWAITING_PAYMENT',
            'totalPrice' : priceAll,
            'totalPaymentPrice' : priceAll,
            'orderer' : orderer,
            'ordererPhoneNumber' : ordererPhone,
            'email' : email,
            'recipient':recipient,
            'recipientPhoneNumber':recipientPhone,
            'requirements':requirements,
            "address": {
                'zipcode':zipcode,
                'city':city,
                'street':street
            }
        };

        $.ajax({
            type: 'post',
            url: '/order/'+customerKey+'/new',
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(orderData),
            success :function(data){
                console.log("deliverySave성공이라해주라 ", data.id);

                // ------ 결제창 띄우기 ------
                tossPayments.requestPayment('CARD',{
                    amount: priceAll,
                    orderId: data.id, //6자 이상
                    orderName: productName + ' 외 ' + (cartLenth-1),
                    customerName: orderer,
                    customerEmail: email,
                    successUrl: 'http://localhost:8088/payment/success',
                    failUrl: 'http://localhost:8088/payment/fail'
                });

                // window.location.reload(true);
            },
            error:function(request, status, error){
                console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
            }
        });

        // if(deliverySave){ 배송지 저장
        //     if(deliveryYN ==null){
        //
        //     } else {
        //         $.ajax({
        //             type: 'post',
        //             url: '/order/'+customerKey+'/deliveryUpdate',
        //             contentType:"application/json;charset=utf-8",
        //             data:JSON.stringify(data),
        //             success :function(data){
        //                 console.log("deliverySave성공이라해주라 ", data);
        //                 // window.location.reload(true);
        //             },
        //             error:function(request, status, error){
        //                 console.log("code : "+request.status+"\n"+"message : "+request.responseText+"\n"+"error : "+error);
        //             }
        //         });
        //     }
        // }

    });

});

function searchAddressFunc() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
            $("#zipcode").val(data.zonecode);
            $("#zipcodeView").val(data.zonecode);
            $("#city").val(data.roadAddress);
            $("#cityView").val(data.roadAddress);
            $("#street").focus();
            // formReg.regAddress.status = true;
        }
    }).open();
}
