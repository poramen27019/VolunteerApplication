<?php
    include("connect.php");

    // $member_id = $_GET['member_id'];
    $member_username = $_GET['member_username'];
    $member_password= $_GET['member_password'];


        $sql = "SELECT * FROM member WHERE member_username = '$member_username'
        AND member_password = '$member_password'";
        $result = array();
        $query = mysqli_query($con,$sql);

        while($row = mysqli_fetch_assoc($query)){
        array_push($result,array("member_username" => $row["member_username"],
                                "member_password" => $row["member_password"],
                                "member_id" => $row["member_id"],
                                "member_fname" => $row["member_fname"],
                                "member_profile" => $row["member_profile"]));

    }

    print json_encode(array('result' => $result));

    mysqli_close($con);
    
    ?>