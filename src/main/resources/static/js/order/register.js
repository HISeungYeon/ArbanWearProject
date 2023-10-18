$(function(){
    // alert("하이");

    let price;
    let priceAll = 0;
    let email = $('inputEmail').val();

    const clientKey = 'test_ck_DLJOpm5QrlxzAkdbmPdrPNdxbWnY' // 테스트용 클라이언트 키
    const tossPayments = TossPayments(clientKey)

    $("#searchAddress").on('click',searchAddressFunc);

    $(".price").text(function(index, element ) {

        console.log("index : ", index, " element : ", element)

        price = Number(element);
        priceAll += price;

        $('#priceAll').text(priceAll.toLocaleString('ko-KR') + '원');
        $('#orderBtn').text(priceAll.toLocaleString('ko-KR') + '원 주문하기');
    });

    $('#orderBtn').click(function (){

        let email = $('#inputEmail').val();
        let orderer = $('#inputOrderer').val();

        console.log("email : ", email, " name : ", orderer, " productName : ", productName, " cartLenth : ", cartLenth);

        // ------ 결제창 띄우기 ------
        tossPayments.requestPayment('CARD', {
            amount: priceAll,
            orderId: '100000', //6자 이상
            orderName: productName + ' 외 ' + (cartLenth-1),
            customerName: orderer,
            customerEmail: email,
            successUrl: 'http://localhost:8088/order/success',
            failUrl: 'http://localhost:8088/order/fail'
            // successUrl: 'http://localhost:8081/api/v1/payments/toss/success',
            // failUrl: 'http://localhost:8081/api/v1/payments/toss/fail'
        });
    });

})

function searchAddressFunc() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분
            $("#zipcode").val(data.zonecode);
            $("#zipcodeView").val(data.zonecode);
            $("#city").val(data.roadAddress);
            $("#cityView").val(data.roadAddress);
            $("#street").focus();
            formReg.regAddress.status = true;
        }
    }).open();
}