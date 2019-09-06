CREATE TABLE IF NOT EXISTS property (
  ppt_id INT NOT NULL PRIMARY KEY,
  pptName VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS pptvalue (
  pptvalue_id INT NOT NULL PRIMARY KEY,
  ppt_id INT NOT NULL,
  pptValue VARCHAR(254) NOT NULL,
  desc VARCHAR(254),
  state VARCHAR(2),
  deleted bit
);

/*
INSERT INTO property VALUES(1,'endpoint_veraz');
INSERT INTO pptvalue VALUES(1,1,'http://localhost:8080/services/idvalidator','to camel endpoint',0,0);
INSERT INTO pptvalue VALUES(2,1,'http://localhost:8088/mockidvalidatorSoap11Binding','to soapui endpoint',1,0);
*/