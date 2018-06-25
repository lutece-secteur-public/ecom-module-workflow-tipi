DROP TABLE IF EXISTS workflow_task_tipi_cf;
DROP TABLE IF EXISTS workflow_tipi_tipi;
DROP TABLE IF EXISTS workflow_task_tipi_refdet_history;

--
-- Table structure for table workflow_task_tipi_cf
--

CREATE TABLE workflow_task_tipi_cf(
id_task INT DEFAULT 0 NOT NULL,
id_state_after_success_payment INT DEFAULT 0 NOT NULL,
id_state_after_failure_payment INT DEFAULT 0 NOT NULL,
id_state_after_canceled_payment INT DEFAULT 0 NOT NULL,
PRIMARY KEY (id_task)
);


--
-- Create table workflow_tipi_tipi
--

CREATE TABLE workflow_tipi_tipi(
ref_det INT DEFAULT 0 NOT NULL,
amount INT DEFAULT 0 NOT NULL,
email VARCHAR(255),
id_op VARCHAR(255),
transaction_result VARCHAR(1),
PRIMARY KEY (ref_det)
);

--
--Create table workflow_task_tipi_refdet_history
--

CREATE TABLE workflow_task_tipi_refdet_history(
id_history INT DEFAULT 0 NOT NULL,
id_task INT DEFAULT 0 NOT NULL,
ref_det INT DEFAULT 0 NOT NULL,
PRIMARY KEY (id_history)
);