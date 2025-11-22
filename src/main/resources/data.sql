USE online_exam_db;

INSERT INTO OnlineWeb (W_ID, WName) VALUES
('web_main', 'University Main Website');

INSERT INTO Student (Student_ID, SName, Phone, Password) VALUES
('s1001', 'Alice Smith', '090123456', 'hash_pass_123'),
('s1002', 'Bob Johnson', '090789123', 'hash_pass_456'),
('s1003', 'Charlie Brown', '091234567', 'hash_pass_777'),
('s1004', 'Diana Prince', '092345678', 'hash_pass_888'),
('s1005', 'Ethan Hunt', '093456789', 'hash_pass_999'),
('s1006', 'Fiona Glenanne', '094567890', 'hash_pass_101'),
('s1007', 'George Costanza', '095678901', 'hash_pass_202'),
('s1008', 'Holly Golightly', '096789012', 'hash_pass_303'),
('s1009', 'Ivy Pepper', '097890123', 'hash_pass_404'),
('s1010', 'Jack Reacher', '098901234', 'hash_pass_505'),
('s1011', 'Kelly Kapowski', '099012345', 'hash_pass_606'),
('s1012', 'Liam Neeson', '090011223', 'hash_pass_707');

INSERT INTO Admin (Admin_ID, Password, OnlineWeb_W_ID) VALUES
('a001', 'admin_hash_789', 'web_main'),
('a002', 'super_admin_hash_111', 'web_main');


INSERT INTO Course (Course_ID, CName, OnlineWeb_W_ID) VALUES
('CS101', 'Intro to Computing', 'web_main'),
('MATH105', 'Calculus I', 'web_main'),
('PHYS101', 'Classical Mechanics', 'web_main'),
('CHEM202', 'Organic Chemistry', 'web_main'),
('HIST301', 'World History Since 1500', 'web_main'),
('ENG404', 'Technical Writing', 'web_main'),
('DS500', 'Data Structures and Algorithms', 'web_main');

INSERT INTO Exam (Exam_ID, EType, RoomNumber, TestDateDATETIME, Duration, Course_Course_ID) VALUES
('e1001', 'M', 'C101', '2025-12-15 09:00:00', '90min', 'CS101'),       -- CS101 Midterm
('e1002', 'F', 'A205', '2026-01-20 14:00:00', '120min', 'CS101'),      -- CS101 Final
('e2001', 'F', 'B100', '2026-01-22 10:00:00', '120min', 'MATH105'),    -- MATH105 Final
('e3001', 'M', 'D105', '2025-12-18 10:30:00', '90min', 'PHYS101'),    -- PHYS101 Midterm
('e4001', 'F', 'E201', '2026-01-25 13:00:00', '120min', 'CHEM202'),    -- CHEM202 Final
('e5001', 'M', 'F303', '2025-12-20 09:00:00', '60min', 'HIST301'),    -- HIST301 Midterm
('e6001', 'M', 'G401', '2025-12-19 15:00:00', '90min', 'ENG404'),     -- ENG404 Midterm
('e7001', 'F', 'H100', '2026-01-28 08:30:00', '150min', 'DS500');      -- DS500 Final

INSERT INTO Student_has_Course (Student_Student_ID, Course_Course_ID) VALUES
('s1001', 'CS101'),    -- Alice is in CS101
('s1001', 'MATH105'), -- Alice is in MATH105
('s1002', 'CS101'),    -- Bob is in CS101
('s1003', 'PHYS101'),  -- Charlie is in PHYS101
('s1004', 'CHEM202'),  -- Diana is in CHEM202
('s1005', 'HIST301'),  -- Ethan is in HIST301
('s1006', 'ENG404'),   -- Fiona is in ENG404
('s1007', 'DS500'),    -- George is in DS500
('s1003', 'CS101'),    -- Charlie is also in CS101
('s1004', 'MATH105'),  -- Diana is also in MATH105
('s1001', 'PHYS101');  -- Alice is also in PHYS101 (Added for result context)

INSERT INTO Student_has_Exam (Student_Student_ID, Exam_Exam_ID) VALUES
('s1001', 'e1002'), -- Alice registered for CS101 Final
('s1001', 'e2001'), -- Alice registered for MATH105 Final
('s1002', 'e1002'), -- Bob registered for CS101 Final
('s1003', 'e3001'), -- Charlie registered for PHYS101 Midterm
('s1003', 'e1001'), -- Charlie registered for CS101 Midterm (for results context)
('s1004', 'e4001'), -- Diana registered for CHEM202 Final
('s1005', 'e5001'), -- Ethan registered for HIST301 Midterm
('s1006', 'e6001'), -- Fiona registered for ENG404 Midterm
('s1007', 'e7001'), -- George registered for DS500 Final
('s1008', 'e7001'), -- Holly registered for DS500 Final
('s1001', 'e3001'); -- Alice registered for PHYS101 Midterm (for results context)


INSERT INTO Result (Student_ID, Exam_ID, Score) VALUES
('s1001', 'e1001', 92), -- Alice's score for the CS101 Midterm
('s1002', 'e1001', 85), -- Bob's score for the CS101 Midterm
('s1003', 'e3001', 78), -- Charlie's score for PHYS101 Midterm
('s1005', 'e5001', 89), -- Ethan's score for HIST301 Midterm
('s1006', 'e6001', 95), -- Fiona's score for ENG404 Midterm
('s1003', 'e1001', 81), -- Charlie's score for CS101 Midterm
('s1001', 'e3001', 75); -- Alice's score for PHYS101 Midterm
