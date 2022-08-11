/* 라벨링된 OCR 바운딩 박스의 정보를 저장 */
INSERT INTO labeled_ocr_bounding_box(labeled_ocr_bounding_box_id, ocr_bounding_box_id, labeled_count) VALUES(1,1,5);
INSERT INTO labeled_ocr_bounding_box(labeled_ocr_bounding_box_id, ocr_bounding_box_id, labeled_count) VALUES(2,2,4);

/* 라벨링된 OCR 텍스트 라벨을 저장 */
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(1,1,'안녕하세요',4,false);
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(2,1,'안녕하신가요',1,false);
INSERT INTO labeled_ocr_text_label(labeled_ocr_text_label_id, labeled_ocr_bounding_box_id, text_label, labeled_count, verification) VALUES(3,2,'감사합니다',4,false);

/* 라벨링된 OCR 텍스트 라벨의 라벨링의 정보(UUID)를 저장 */
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(1,1,'uuid-1');
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(2,1,'uuid-2');
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(3,1,'uuid-3');
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(4,1,'uuid-4');

INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(5,2,'uuid-5');

INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(6,3,'uuid-6');
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(7,3,'uuid-7');
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(8,3,'uuid-8');
INSERT INTO labeled_ocr_labeling(labeled_ocr_labeling_id, labeled_ocr_text_label_id, labeling_uuid) VALUES(9,3,'uuid-9');