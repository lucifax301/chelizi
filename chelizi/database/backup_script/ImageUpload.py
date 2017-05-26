# coding=utf-8
import hashlib
import xlrd
from xlutils.copy import copy
import os


def write_excel(row, col, celldata, sheet):
    try:
        olddata = xlrd.open_workbook('headIcon.xls', formatting_info=True)
        rowdata = copy(olddata)
        data = rowdata.get_sheet(sheet)
        data.write(row, col, celldata)
        rowdata.save('headIcon.xls')
    except Exception, e:
        print str(e)
        print "Do not open the file.xls when the program is running."
        exit(0)


if __name__ == "__main__":

    path = "照片".decode("utf8") + os.sep  # 头像路径
    schoolName = "深港".decode("utf8")  # 导入头像教练的importSrc字段值

    for rt, dirs, files in os.walk(path):
        print rt
        print dirs
        i = 0
        for image in files:
            write_excel(i, 0, image[:-4], 0)
            print image.encode("GB2312")
            image_string = open(path + image)
            image_file = image_string.read() + image_string.read()
            write_excel(i, 1, hashlib.md5(image_file).hexdigest() + ".jpg", 0)
            write_excel(i, 3, "update t_u_coach set headIcon = '" + hashlib.md5(image_file).hexdigest() + ".jpg" + "' where importSrc = '" + schoolName + "' and name = '" + image[:-4] + "';", 0)
            print hashlib.md5(image_file).hexdigest()
            image_string.close()
            os.rename(path + image, path + hashlib.md5(image_file).hexdigest() + ".jpg")
            i += 1
