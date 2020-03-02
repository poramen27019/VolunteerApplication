<?php
    include("connect.php");

    $member_fname = $_POST['member_fname'];
    $member_lname = $_POST['member_lname'];
    $member_phone= $_POST['member_phone'];
    $member_profile = $_POST['member_profile'];

    $name_img = md5(uniqid(mt_rand(), true));
    $ImagePath = "upload_profile/$name_img.jpg";

    $member_id = $_POST['member_id'];

    
        $sql ="UPDATE member SET member_fname='$member_fname',member_lname='$member_lname',member_phone='$member_phone',member_profile='$ImagePath'  
        WHERE member_id='$member_id' " ;
       
       if(mysqli_query($con,$sql)){
        file_put_contents($ImagePath,base64_decode($member_profile));
        echo "สำเร็จ";
    }else{
        echo "ไม่";
    }
    

    mysqli_close($con);
    
    ?>