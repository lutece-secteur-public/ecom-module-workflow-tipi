DROP TABLE IF EXISTS workflow_task_tipi_cf;

--
-- Table structure for table task_edit_record_cf
--

CREATE TABLE workflow_task_tipi_cf(
id_task INT DEFAULT 0 NOT NULL,
id_state_after_success_payment INT DEFAULT 0 NOT NULL,
id_state_after_failure_payment INT DEFAULT 0 NOT NULL,
id_state_after_canceled_payment INT DEFAULT 0 NOT NULL,
PRIMARY KEY (id_task)
);


