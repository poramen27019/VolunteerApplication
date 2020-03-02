<?php
    include("connect.php");
 
    // $member_fname = $_POST['fname'];
    $member_id = $_POST['userid'];
    $member_username= $_POST['username'];
    $member_password = $_POST['password'];
    $member_email = $_POST['email'];
    // $member_profile = $_POST['member_profile'];

    // $name_img = md5(uniqid(mt_rand(), true));
    $ImagePath = "upload_profile/startprofile22222222222.jpg";

        $sql ="INSERT INTO member (member_id,member_username,member_password,member_email,member_profile) 
        VALUES ('$member_id',
        '$member_username',
        '$member_password',
        '$member_email',
        '$ImagePath')";


$result = mysqli_query($con,$sql);

mysqli_close($con);
    ?>