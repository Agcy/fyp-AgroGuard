import pandas as pd


def data_manage(datapath):
    covid_data = pd.read_csv(datapath)
    data1 = covid_data.copy()
    subset1 = data1[data1['code'] == 'WHEAT_WORLD_19']
    subset2 = data1[data1['code'] == 'COURSE_GRAIN_WORLD_21']
    subset3 = data1[data1['code'] == 'CORN_WORLD_23']
    subset4 = data1[data1['code'] == 'RICE_WORLD_25']
    subset5 = data1[data1['code'] == 'COTTON_WORLD_27']
    subset6 = data1[data1['code'] == 'SOYBEAN_WORLD_28']
    subset7 = data1[data1['code'] == 'SOYBEAN_OIL_WORLD_30']
    subset8 = data1[data1['code'] == 'OILSEEDS_WORLD_10']
    subset9 = data1[data1['code'] == 'GRAINS_WORLD_08']

    subset1.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_WHEAT.csv', index=False)
    subset2.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_COURSE_GRAIN.csv', index=False)
    subset3.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_CORN.csv', index=False)
    subset4.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_RICE.csv', index=False)
    subset5.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_COTTON.csv', index=False)
    subset6.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_SOYBEAN.csv', index=False)
    subset7.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_SOYBEAN_OIL.csv', index=False)
    subset8.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_OILSEEDS.csv', index=False)
    subset9.to_csv('D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_GRAINS.csv', index=False)


    print(covid_data.columns)

    return covid_data


if __name__ == '__main__':
    datapath = 'D:\\BIRD\\year4\\FinalYearProject\\code\\AgroGuard_Backend\\src\\main\\resources\\csv\\WASDE_DATA_0cdaff592f19dc15ab6e8eba9102bc11.csv'
    data_manage(datapath)
