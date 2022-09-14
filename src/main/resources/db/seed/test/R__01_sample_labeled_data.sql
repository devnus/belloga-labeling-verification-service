/* TextLabel 검증을 위한 seed */

/* 라벨링된 OCR 바운딩 박스의 정보를 저장 */
INSERT INTO labeled_ocr_bounding_box(labeled_ocr_bounding_box_id, ocr_bounding_box_id, labeled_count, verification_finish) VALUES(1,1,5,false);
INSERT INTO labeled_ocr_bounding_box(labeled_ocr_bounding_box_id, ocr_bounding_box_id, labeled_count, verification_finish) VALUES(2,2,4,false);

/* 라벨링된 OCR 텍스트 라벨을 저장 */
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(1,1,'안녕하세요',4,null);
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(2,1,'안녕하신가요',1,null);
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(3,2,'감사합니다',4,null);

/* 라벨링된 OCR 텍스트 라벨의 라벨링의 정보(UUID)를 저장 */
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(1,1,'uuid-1',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(2,1,'uuid-2',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(3,1,'uuid-3',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(4,1,'uuid-4',false);

INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(5,2,'uuid-5',false);

INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(6,3,'uuid-6',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(7,3,'uuid-7',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(8,3,'uuid-8',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(9,3,'uuid-9',false);

/* Labeling 검증을 위한 seed */

/* 라벨링된 OCR 바운딩 박스의 정보를 저장 */
INSERT INTO labeled_ocr_bounding_box(labeled_ocr_bounding_box_id, ocr_bounding_box_id, labeled_count, verification_finish) VALUES(3,3,5,false);

/* 라벨링된 OCR 텍스트 라벨을 저장 */
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(4,3,'hi',4,true);
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(5,3,'hello',1,false);

/* 라벨링된 OCR 텍스트 라벨의 라벨링의 정보(UUID)를 저장 */
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(10,4,'uuid-10',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(11,4,'uuid-11',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(12,4,'uuid-12',false);
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(13,4,'uuid-13',false);

INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid, verification_finish) VALUES(14,5,'uuid-14',false);