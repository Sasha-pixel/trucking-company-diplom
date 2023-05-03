package com.example.truck.business.service.util;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import io.tesler.core.crudma.bc.BcIdentifier;

@UtilityClass
public class DrillDownUtils {

	private static final String BC_URL_TEMPLATE = "screen/%s/view/%s/%s/%d";

	private static final String BC_URL_PARENT_TEMPLATE = "screen/%s/view/%s/%s/%d/%s/%d";

	private static final String BC_URL_PARENT_TO_LIST_TEMPLATE = "screen/%s/view/%s/%s/%d/%s";

	private static final String SHORT_BC_URL_PARENT_TO_LIST_TEMPLATE = "%s/%s/%d/%s";

	private static final String SHORT_BC_URL_TEMPLATE = "%s/%s/%d";

	private static final String VIEW_URL_TEMPLATE = "screen/%s/view/%s";

	/**
	 * Формирование ссылки для перехода на запись на виджете внутри приложения
	 *
	 * @param screenName наименование экрана для формирования ссылки
	 * @param viewName наименование представления для формирования ссылки
	 * @param bc бизнес-компонента для формирования ссылки
	 * @param id id-записи на виджете с данной bc
	 * @return ссылка на запись на виджете
	 */
	public static String bcUrl(final Screens screenName, final Views viewName, final BcIdentifier bc, final Long id) {
		return String.format(bc == null ? VIEW_URL_TEMPLATE : BC_URL_TEMPLATE, screenName, viewName, bc, id);
	}

	/**
	 * Формирование ссылки для перехода на запись на виджете, построенном на бизнес-компоненте, имеющей родителя,
	 * с переходом на другой экран внутри приложения
	 *
	 * @param screenName наименование экрана для формирования ссылки
	 * @param viewName наименование представления для формирования ссылки
	 * @param parentBc родительская бизнес-компонента для формирования ссылки
	 * @param parentId id-записи на виджете с данной parentBc
	 * @param bc дочерняя бизнес-компонента для формирования ссылки
	 * @param id id-записи на виджете с данной bc
	 * @return ссылка на запись на виджете
	 */
	public static String bcUrl(final Screens screenName, final Views viewName, final BcIdentifier parentBc,
														 final Long parentId, final BcIdentifier bc,
														 final Long id) {
		return String.format(BC_URL_PARENT_TEMPLATE, screenName, viewName, parentBc, parentId, bc, id);
	}

	/**
	 * Формирование ссылки для перехода на виджет, построенный на бизнес-компоненте, имеющей родителя,
	 * с переходом на другой экран внутри приложения
	 *
	 * @param screenName наименование экрана для формирования ссылки
	 * @param viewName наименование представления для формирования ссылки
	 * @param parentBc родительская бизнес-компонента для формирования ссылки
	 * @param parentId id-записи на виджете с данной parentBc
	 * @param bc дочерняя бизнес-компонента для формирования ссылки
	 * @return ссылка на виджет
	 */
	public static String bcUrl(final Screens screenName, final Views viewName, final BcIdentifier parentBc,
														 final Long parentId,
														 final BcIdentifier bc) {
		return String.format(BC_URL_PARENT_TO_LIST_TEMPLATE, screenName, viewName, parentBc, parentId, bc);
	}

	/**
	 * Формирование ссылки для перехода на виджет, построенный на бизнес-компоненте, имеющей родителя,
	 * с переходом на другой экран внутри приложения
	 *
	 * @param screenViewName наименование экрана и представления для формирования ссылки
	 * @param parentBc родительская бизнес-компонента для формирования ссылки
	 * @param parentId id-записи на виджете с данной parentBc
	 * @param bc дочерняя бизнес-компонента для формирования ссылки
	 * @return ссылка на виджет
	 */
	public static String bcUrl(final ScreenViews screenViewName, final BcIdentifier parentBc, final Long parentId, final BcIdentifier bc) {
		return String.format(SHORT_BC_URL_PARENT_TO_LIST_TEMPLATE, screenViewName.toString(), parentBc, parentId, bc);
	}

	/**
	 * Формирование ссылки для перехода на запись на виджете внутри приложения
	 *
	 * @param screenViewName наименование экрана и представления для формирования ссылки
	 * @param bc бизнес-компонента для формирования ссылки
	 * @param id id-записи на виджете с данной bc
	 * @return ссылка на запись на виджете
	 */
	public static String bcUrl(final ScreenViews screenViewName, final BcIdentifier bc, final Long id) {
		return String.format(bc == null ? screenViewName.toString() : SHORT_BC_URL_TEMPLATE, screenViewName, bc, id);
	}

	/**
	 * Формирование ссылки для перехода на представление на экране внутри приложения
	 *
	 * @param screenName наименование экрана для формирования ссылки
	 * @param viewName наименование представления для формирования ссылки
	 * @return ссылка на представление
	 */
	public static String viewUrl(final Screens screenName, final Views viewName) {
		return bcUrl(screenName, viewName, null, null);
	}

	/**
	 * Формирование ссылки для перехода на представление на экране внутри приложения
	 *
	 * @param screenViewName наименование экрана и представления для формирования ссылки
	 * @return ссылка на представление
	 */
	public static String viewUrl(final ScreenViews screenViewName) {
		return bcUrl(screenViewName, null, null);
	}

	@RequiredArgsConstructor
	public enum Screens {

		ORDER_SCREEN("order"),
		DRIVER_SCREEN("driver");

		private final String name;

		@Override
		public String toString() {
			return name;
		}
	}

	@RequiredArgsConstructor
	public enum Views {

		ORDER_LIST_VIEW("orderlist"),
		CREATE_ORDER_VIEW("createorderform"),
		EDIT_ORDER_VIEW("editorderform"),

		DRIVER_LIST_VIEW("driverlist"),
		CREATE_DRIVER_VIEW("createdriverform"),
		EDIT_DRIVER_VIEW("editdriverform");

		private final String name;

		@Override
		public String toString() {
			return name;
		}
	}

	@RequiredArgsConstructor
	public enum ScreenViews {

		ORDER_SCREEN_ORDER_LIST_VIEW(Screens.ORDER_SCREEN, Views.ORDER_LIST_VIEW),
		ORDER_SCREEN_CREATE_ORDER_VIEW(Screens.ORDER_SCREEN, Views.CREATE_ORDER_VIEW),
		ORDER_SCREEN_EDIT_ORDER_VIEW(Screens.ORDER_SCREEN, Views.EDIT_ORDER_VIEW),

		DRIVER_SCREEN_DRIVER_LIST_VIEW(Screens.DRIVER_SCREEN, Views.DRIVER_LIST_VIEW),
		DRIVER_SCREEN_CREATE_DRIVER_VIEW(Screens.DRIVER_SCREEN, Views.CREATE_DRIVER_VIEW),
		DRIVER_SCREEN_EDIT_DRIVER_VIEW(Screens.DRIVER_SCREEN, Views.EDIT_DRIVER_VIEW);

		private final Screens screen;

		private final Views view;

		@Override
		public String toString() {
			return String.format(VIEW_URL_TEMPLATE, screen, view);
		}
	}

}
