<?php

  // borrowed from https://phpdelusions.net/pdo#dsn

  $host = 'localhost';
  $db   = 'id8664895_cosc3506db';
  $user = 'id8664895_cosc3506u';
  $pass = 'bj6"{rVA`R/ar>!<';
  $charset = 'utf8mb4'; // not sure what this is - Brian

  $options = [
    PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES   => false,
  ];

  $dsn = "mysql:host=$host;dbname=$db;charset=$charset;";

  try {
    $pdo = new PDO($dsn, $user, $pass, $options);
    echo "<script>console.log('Connected to DB');</script>";
  } catch (PDOException $e) {
    echo "<script>console.error('DB connection error: " .  $e->getMessage(). "');</script>";
    throw new PDOException($e->getMessage(), (int)$e->getCode());
  }

?>
